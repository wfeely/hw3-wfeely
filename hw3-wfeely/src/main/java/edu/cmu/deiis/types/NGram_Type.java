
/* First created by JCasGen Wed Sep 11 13:44:28 EDT 2013 */
package edu.cmu.deiis.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;

/** 
 * Updated by JCasGen Fri Sep 27 14:05:31 EDT 2013
 * @generated */
public class NGram_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (NGram_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = NGram_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new NGram(addr, NGram_Type.this);
  			   NGram_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new NGram(addr, NGram_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = NGram.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.deiis.types.NGram");
 
  /** @generated */
  final Feature casFeat_elements;
  /** @generated */
  final int     casFeatCode_elements;
  /** @generated */ 
  public int getElements(int addr) {
        if (featOkTst && casFeat_elements == null)
      jcas.throwFeatMissing("elements", "edu.cmu.deiis.types.NGram");
    return ll_cas.ll_getRefValue(addr, casFeatCode_elements);
  }
  /** @generated */    
  public void setElements(int addr, int v) {
        if (featOkTst && casFeat_elements == null)
      jcas.throwFeatMissing("elements", "edu.cmu.deiis.types.NGram");
    ll_cas.ll_setRefValue(addr, casFeatCode_elements, v);}
    
   /** @generated */
  public int getElements(int addr, int i) {
        if (featOkTst && casFeat_elements == null)
      jcas.throwFeatMissing("elements", "edu.cmu.deiis.types.NGram");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_elements), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_elements), i);
  return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_elements), i);
  }
   
  /** @generated */ 
  public void setElements(int addr, int i, int v) {
        if (featOkTst && casFeat_elements == null)
      jcas.throwFeatMissing("elements", "edu.cmu.deiis.types.NGram");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_elements), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_elements), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_elements), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_elementType;
  /** @generated */
  final int     casFeatCode_elementType;
  /** @generated */ 
  public String getElementType(int addr) {
        if (featOkTst && casFeat_elementType == null)
      jcas.throwFeatMissing("elementType", "edu.cmu.deiis.types.NGram");
    return ll_cas.ll_getStringValue(addr, casFeatCode_elementType);
  }
  /** @generated */    
  public void setElementType(int addr, String v) {
        if (featOkTst && casFeat_elementType == null)
      jcas.throwFeatMissing("elementType", "edu.cmu.deiis.types.NGram");
    ll_cas.ll_setStringValue(addr, casFeatCode_elementType, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sentenceId;
  /** @generated */
  final int     casFeatCode_sentenceId;
  /** @generated */ 
  public int getSentenceId(int addr) {
        if (featOkTst && casFeat_sentenceId == null)
      jcas.throwFeatMissing("sentenceId", "edu.cmu.deiis.types.NGram");
    return ll_cas.ll_getIntValue(addr, casFeatCode_sentenceId);
  }
  /** @generated */    
  public void setSentenceId(int addr, int v) {
        if (featOkTst && casFeat_sentenceId == null)
      jcas.throwFeatMissing("sentenceId", "edu.cmu.deiis.types.NGram");
    ll_cas.ll_setIntValue(addr, casFeatCode_sentenceId, v);}
    
  
 
  /** @generated */
  final Feature casFeat_order;
  /** @generated */
  final int     casFeatCode_order;
  /** @generated */ 
  public int getOrder(int addr) {
        if (featOkTst && casFeat_order == null)
      jcas.throwFeatMissing("order", "edu.cmu.deiis.types.NGram");
    return ll_cas.ll_getIntValue(addr, casFeatCode_order);
  }
  /** @generated */    
  public void setOrder(int addr, int v) {
        if (featOkTst && casFeat_order == null)
      jcas.throwFeatMissing("order", "edu.cmu.deiis.types.NGram");
    ll_cas.ll_setIntValue(addr, casFeatCode_order, v);}
    
  
 
  /** @generated */
  final Feature casFeat_coveredText;
  /** @generated */
  final int     casFeatCode_coveredText;
  /** @generated */ 
  public String getCoveredText(int addr) {
        if (featOkTst && casFeat_coveredText == null)
      jcas.throwFeatMissing("coveredText", "edu.cmu.deiis.types.NGram");
    return ll_cas.ll_getStringValue(addr, casFeatCode_coveredText);
  }
  /** @generated */    
  public void setCoveredText(int addr, String v) {
        if (featOkTst && casFeat_coveredText == null)
      jcas.throwFeatMissing("coveredText", "edu.cmu.deiis.types.NGram");
    ll_cas.ll_setStringValue(addr, casFeatCode_coveredText, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public NGram_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_elements = jcas.getRequiredFeatureDE(casType, "elements", "uima.cas.FSArray", featOkTst);
    casFeatCode_elements  = (null == casFeat_elements) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_elements).getCode();

 
    casFeat_elementType = jcas.getRequiredFeatureDE(casType, "elementType", "uima.cas.String", featOkTst);
    casFeatCode_elementType  = (null == casFeat_elementType) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_elementType).getCode();

 
    casFeat_sentenceId = jcas.getRequiredFeatureDE(casType, "sentenceId", "uima.cas.Integer", featOkTst);
    casFeatCode_sentenceId  = (null == casFeat_sentenceId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sentenceId).getCode();

 
    casFeat_order = jcas.getRequiredFeatureDE(casType, "order", "uima.cas.Integer", featOkTst);
    casFeatCode_order  = (null == casFeat_order) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_order).getCode();

 
    casFeat_coveredText = jcas.getRequiredFeatureDE(casType, "coveredText", "uima.cas.String", featOkTst);
    casFeatCode_coveredText  = (null == casFeat_coveredText) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_coveredText).getCode();

  }
}



    