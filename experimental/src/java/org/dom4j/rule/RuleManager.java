/*
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 * 
 * This software is open source. 
 * See the bottom of this file for the licence.
 * 
 * $Id: RuleManager.java,v 1.1 2001/02/01 23:32:46 jstrachan Exp $
 */

package org.dom4j.rule;

import java.util.HashMap;

import org.dom4j.Node;


/** <p><code>RuleManager</code> manages a set of rules such that a rule
  * can be found for a given DOM4J Node using the XSLT processing model.</p>
  *
  * @author <a href="mailto:james.strachan@metastuff.com">James Strachan</a>
  * @version $Revision: 1.1 $
  */
public class RuleManager {

    /** Map of SortedSets of Rules indexed by mode */
    private HashMap modeToRuleSets = new HashMap();
    
    public RuleManager() {
    }

    public void addRule(Rule rule) {
        int matchType = rule.getMatchType();
        RuleSet[] ruleSets = getRuleSets( rule.getMode() );
        
        getRuleSet( ruleSets, matchType ).addRule( rule );
        if ( matchType != Pattern.ANY_NODE ) {
            getRuleSet( ruleSets, Pattern.ANY_NODE ).addRule( rule );
        }
    }
    
    public void removeRule(Rule rule) {
        int matchType = rule.getMatchType();
        RuleSet[] ruleSets = getRuleSets( rule.getMode() );
        
        getRuleSet( ruleSets, matchType ).removeRule( rule );
        if ( matchType != Pattern.ANY_NODE ) {
            getRuleSet( ruleSets, Pattern.ANY_NODE ).removeRule( rule );
        }
    }

    /** Performs an XSLT processing model match for the rule
      * which matches the given Node the best.
      *
      * @param mode is the mode associated with the rule if any
      * @param node is the DOM4J Node to match against
      * @return the matching Rule or no rule if none matched
      */
    public Rule getMatchingRule(String mode, Node node) {
        RuleSet[] ruleSets = getRuleSets( mode );
        int matchType = node.getNodeType();
        if ( matchType < 0 || matchType >= ruleSets.length ) {
            matchType = Pattern.ANY_NODE;
        }
        RuleSet ruleSet = ruleSets[ matchType ];
        return ruleSet.getMatchingRule( node );
    }
    
    public void clear() {
        modeToRuleSets.clear();
    }
    
    
    protected RuleSet[] getRuleSets( String mode ) {
        RuleSet[] ruleSets = (RuleSet[]) modeToRuleSets.get(mode);
        if ( ruleSets == null ) {
            ruleSets = new RuleSet[ Pattern.NUMBER_OF_TYPES ];
            modeToRuleSets.put(mode, ruleSets);
        }
        return ruleSets;
    }
    
    protected RuleSet getRuleSet( RuleSet[] ruleSets, int matchType ) {
        if ( matchType >= Pattern.NUMBER_OF_TYPES ) {
            matchType = Pattern.ANY_NODE;
        }
        RuleSet ruleSet = ruleSets[ matchType ];
        if ( ruleSet == null ) {
            ruleSet = new RuleSet();
            ruleSets[ matchType ] = ruleSet;
        }
        return ruleSet;
    }

}




/*
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 *
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 *
 * 3. The name "DOM4J" must not be used to endorse or promote
 *    products derived from this Software without prior written
 *    permission of MetaStuff, Ltd.  For written permission,
 *    please contact dom4j-info@metastuff.com.
 *
 * 4. Products derived from this Software may not be called "DOM4J"
 *    nor may "DOM4J" appear in their names without prior written
 *    permission of MetaStuff, Ltd. DOM4J is a registered
 *    trademark of MetaStuff, Ltd.
 *
 * 5. Due credit should be given to the DOM4J Project
 *    (http://dom4j.org/).
 *
 * THIS SOFTWARE IS PROVIDED BY METASTUFF, LTD. AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * METASTUFF, LTD. OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright 2001 (C) MetaStuff, Ltd. All Rights Reserved.
 *
 * $Id: RuleManager.java,v 1.1 2001/02/01 23:32:46 jstrachan Exp $
 */
