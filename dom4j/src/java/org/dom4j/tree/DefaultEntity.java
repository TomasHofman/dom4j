package org.dom4j.tree;

import org.dom4j.Element;
import org.dom4j.Node;

/** <p><code>DefaultEntity</code> is the DOM4J default implementation
  * of a singly linked, read-only XML entity.</p>
  *
  * <p>Often this node needs to be created and then the text content added
  * later (for example in SAX) so this implementation allows a call to 
  * {@link #setText} providing the entity has no text already.
  *
  * @author <a href="mailto:james.strachan@metastuff.com">James Strachan</a>
  * @version $Revision: 1.2 $
  */
public class DefaultEntity extends AbstractEntity {

    /** The name of the <code>Entity</code> */
    protected String name;

    /** The text of the <code>Entity</code> */
    protected String text;

    /** A default constructor for implementors to use.
      */
    protected DefaultEntity() {
    }

    /** Creates the <code>Entity</code> with the specified name
      *
      * @param name is the name of the entity
      */
    public DefaultEntity(String name) {
        this.name = name;
    }

    /** Creates the <code>Entity</code> with the specified name
      * and text.
      *
      * @param name is the name of the entity
      * @param text is the text of the entity
      */
    public DefaultEntity(String name, String text) {
        this.name = name;
        this.text = text;
    }

    /** @return the name of the entity
      */
    public String getName() {
        return name;
    }

    /** @return the text of the entity
      */
    public String getText() {
        return text;
    }
    
    /** sets the value of the entity if it is not defined yet
      * otherwise an <code>UnsupportedOperationException</code> is thrown
      * as this class is read only.
      */
    public void setText(String text) {
        if (this.text != null) {
            this.text = text;
        }
        else {
            throw new UnsupportedOperationException( 
                "This Entity is read-only. It cannot be modified" 
            );
        }
    }
    
    protected Node createXPathNode(Element parent) {
        return new XPathEntity( parent, getName(), getText() );
    }
}
