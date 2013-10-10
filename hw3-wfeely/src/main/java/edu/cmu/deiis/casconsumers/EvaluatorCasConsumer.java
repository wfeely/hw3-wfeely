/** EvaluatorCasConsumer.java
 * @author Weston Feely
 */

package edu.cmu.deiis.casconsumers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;
import org.apache.uima.util.ProcessTrace;
import org.cleartk.ne.type.NamedEntityMention;

import edu.cmu.deiis.types.AnswerScore;
import edu.cmu.deiis.types.NGram;
import edu.cmu.deiis.types.Question;

/**
 * A simple CAS consumer that prints the results tables and precision at N for each CAS.
 */
public class EvaluatorCasConsumer extends CasConsumer_ImplBase {

  private double totalPrecision = 0.0;

  private int numCases = 0;

  /**
   * Initializes the CAS consumer.
   */
  public void initialize() throws ResourceInitializationException {
  }

  /**
   * Processes the CAS to rank answer scores, calculate precision at N, and print precision at N
   * with results tables to console.
   * 
   * @param aCAS
   *          a CAS which has been populated by the TAEs
   * 
   * @throws ResourceProcessException
   *           if there is an error in processing the Resource
   * 
   * @see org.apache.uima.collection.base_cpm.CasObjectProcessor#processCas(org.apache.uima.cas.CAS)
   */
  public void processCas(CAS aCAS) throws ResourceProcessException {
    JCas aJCas;
    try {
      aJCas = aCAS.getJCas();
    } catch (CASException e) {
      throw new ResourceProcessException(e);
    }
    // get question
    FSIndex questionIndex = aJCas.getAnnotationIndex(Question.type);
    Iterator questionIter = questionIndex.iterator();
    Question question = null;
    while (questionIter.hasNext())
      question = (Question) questionIter.next();
    // HW3: get named entities in question span
    ArrayList<NamedEntityMention> questionNamedEntities = new ArrayList<NamedEntityMention>();
    FSIndex neQuestionIndex = aJCas.getAnnotationIndex(NamedEntityMention.type);
    Iterator neQuestionIter = neQuestionIndex.iterator();
    while (neQuestionIter.hasNext()) {
      NamedEntityMention ne = (NamedEntityMention) neQuestionIter.next();
      // make sure named entity is within the span of the answer and its type isn't null      
      if ((ne.getBegin() >= question.getBegin()) && (ne.getEnd() <= question.getEnd())
              && (ne.getMentionType() != null))
        questionNamedEntities.add(ne);
    }
    // HW3: check to see if the question has any named entities at all
    boolean namedEntityFlag = true;
    if ((int) questionNamedEntities.size() == 0)
      namedEntityFlag = false;
    // get answer scores
    FSIndex answerScoreIndex = aJCas.getAnnotationIndex(AnswerScore.type);
    // initialize N (number of correct answers)
    int N = 0;
    // create arrayList for ranking
    AnswerScore[] ranking = new AnswerScore[answerScoreIndex.size()];
    // create arrayList for answerScores
    ArrayList<AnswerScore> answerScores = new ArrayList<AnswerScore>();
    // loop over answer scores
    Iterator answerScoreIter = answerScoreIndex.iterator();
    while (answerScoreIter.hasNext()) {
      // grab an answer score
      AnswerScore answerScore = (AnswerScore) answerScoreIter.next();
      // increment N for correct answers
      if (answerScore.getAnswer().getIsCorrect())
        N++;
      // put answerScores into an arrayList
      answerScores.add(answerScore);
    }
    // HW3: adjust scores in each answerScore object, based on matching named entities
    if (namedEntityFlag) {
      for (AnswerScore answerScore : answerScores) {
        // get named entity mentions for this answer
        ArrayList<NamedEntityMention> answerNamedEntities = new ArrayList<NamedEntityMention>();
        FSIndex neAnswerIndex = aJCas.getAnnotationIndex(NamedEntityMention.type);
        Iterator neAnswerIter = neAnswerIndex.iterator();
        while (neAnswerIter.hasNext()) {
          NamedEntityMention ne = (NamedEntityMention) neAnswerIter.next();
          // make sure named entity is within the span of the answer and its type isn't null
          if ((ne.getBegin() >= answerScore.getBegin()) && (ne.getEnd() <= answerScore.getEnd())
                  && (ne.getMentionType() != null))
            answerNamedEntities.add(ne);
        }
        // compare named entity mentions for question and this answer
        int matchNE = 0;
        for (int i = 0; i < questionNamedEntities.size(); i++) {
          for (int j = 0; j < answerNamedEntities.size(); j++) {
            NamedEntityMention qNE = (NamedEntityMention) questionNamedEntities.get(i);
            NamedEntityMention aNE = (NamedEntityMention) answerNamedEntities.get(j);
            if (qNE.getCoveredText().equals(aNE.getCoveredText())) {
              // increment matching named entities count
              matchNE++;
            }
          }
        }
        // adjust score for this answer score, weighting named entity matches by 1/4
        double ngramScore = 0.75 * answerScore.getScore();
        double neScore = 0.25 * ((double) matchNE / (double) questionNamedEntities.size());
        answerScore.setScore(ngramScore + neScore);
      }
    }
    // sort answerScores arrayList
    for (int i = 0; i < ranking.length; i++) {
      double bestScore = 0.0;
      int bestIndex = 0;
      for (int j = 0; j < answerScores.size(); j++) {
        if (answerScores.get(j).getScore() > bestScore) {
          bestScore = answerScores.get(j).getScore();
          bestIndex = j;
        }
      }
      ranking[i] = answerScores.get(bestIndex);
      answerScores.remove(bestIndex);
    }
    // count up correct answers in top N of ranking
    int numCorrect = 0;
    for (int i = 0; i < N; i++) {
      if (ranking[i].getAnswer().getIsCorrect() == true)
        numCorrect++;
    }
    // calculate precision at N
    double precisionAtN = ((double) numCorrect / (double) N);
    // print question to stdout
    System.out.println("Question: " + question.getCoveredText());
    // print each answerScore to stdout
    for (int i = 0; i < ranking.length; i++) {
      AnswerScore answerScore = (AnswerScore) ranking[i];
      // print "+" for isCorrect "-" otherwise
      if (answerScore.getAnswer().getIsCorrect())
        System.out.print("+ ");
      else
        System.out.print("- ");
      // print score
      System.out.print((Math.floor(answerScore.getScore() * 100) / 100) + " ");
      // print covered text of answer
      System.out.println(answerScore.getAnswer().getCoveredText());
    }
    // print precision at N
    System.out.println("Precision at " + N + ": " + precisionAtN + "\n");
    // sum up precision for average precision calculations
    totalPrecision += precisionAtN;
    numCases++;
  }

  /**
   * Prints the average precision at the end of a batch of CASes.
   */
  public void collectionProcessComplete(ProcessTrace arg0) throws ResourceProcessException,
          IOException {
    System.out.println("Average Precision: " + (totalPrecision / numCases) + "\n");
  }
}