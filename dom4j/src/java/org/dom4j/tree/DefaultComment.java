package org.dom4j.tree;

import org.dom4j.Comment;
import org.dom4j.TreeVisitor;

/** <p><code>DefaultComment</code> is the default DOM4J implementation of a 
  * singly linked read only XML Comment.</p>
  *
  * @author <a href="mailto:james.strachan@metastuff.com">James Strachan</a>
  * @version $Revision: 1.1 $
  */
public class DefaultComment extends AbstractComment implements Comment {

    /** Text of the <code>Comment</code> node */
    protected String text;

    /** @param text is the Comment text
      */
    public DefaultComment(String text) {
	this.text = text;
    }

    public String getText() {
	return text;
    }
    
}
