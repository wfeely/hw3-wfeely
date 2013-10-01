/** EvaluationCasConsumer.java
 * @author Weston Feely
 */

package edu.cmu.deiis.casconsumers;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;

import edu.cmu.deiis.types.AnswerScore;
import edu.cmu.deiis.types.Question;

/**
 * A simple CAS consumer that prints the results tables and precision at N for each CAS.
 */
public class EvaluatorCasConsumer extends CasConsumer_ImplBase {
  /**
   * Name of configuration parameter that must be set to the path of a directory into which the
   * output files will be written.
   */
  public static final String PARAM_OUTPUTDIR = "OutputDirectory";

  private File mOutputDir;

  private int mDocNum;

  public void initialize() throws ResourceInitializationException {
    mDocNum = 0;
    mOutputDir = new File((String) getConfigParameterValue(PARAM_OUTPUTDIR));
    if (!mOutputDir.exists()) {
      mOutputDir.mkdirs();
    }
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
    System.out.println("Precision at " + N + ": " + precisionAtN);
    System.out.println();
  }
}