package hudson.plugins.violations.types.fxcop;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

/**
 * Internal set containing rules for FxCop
 *
 * @author Erik Ramfelt
 */
public class FxCopRuleSet {

    private transient Map<String, FxCopRule> rules = new HashMap<String, FxCopRule>();

    /***
     * Parse the element and insert the rule into the rule set.
     * @param element the element <Rule>
     */
    public void addRule(Element element) {
        FxCopRule rule = new FxCopRule(element.getAttribute("TypeName"), element.getAttribute("Category"), element.getAttribute("CheckId"));
        rule.setUrl(getNamedTagText(element, "Url"));
        rule.setDescription(getNamedTagText(element, "Description"));
        rule.setName(getNamedTagText(element, "Name"));

        rules.put(getRuleKey(rule.getCategory(), rule.getCheckId()), rule);
    }

    /**
     * Returns the text value of the named child element if it exists
     * @param element the element to check look for child elements
     * @param tagName the name of the child element
     * @return the text value; or "" if no element was found
     */
    private String getNamedTagText(Element element, String tagName) {
        Element foundElement = XmlElementUtil.getFirstElementByTagName(element, tagName);
        if (foundElement == null) {
            return "";
        } else {
            return foundElement.getTextContent();
        }
    }

    /**
     * Returns if the rule set contains a rule for the specified category and id
     * @param category the rule category
     * @param checkId the rule id
     * @return
     */
    public boolean contains(String category, String checkId) {
        return (rules.containsKey(getRuleKey(category, checkId)));
    }

    /**
     * Returns the specified rule if it exists
     * @param category the rule category
     * @param checkId the id of the rule
     * @return the rule; null otherwise
     */
    public FxCopRule getRule(String category, String checkId) {
        String key = getRuleKey(category, checkId);
        FxCopRule rule = null;
        if (rules.containsKey(key)) {
            rule = rules.get(key);
        }
        return rule;
    }

    /**
     * Returns the key for the map
     * @param category category of the rule
     * @param checkId id of the rule
     * @return category + "#" + checkid
     */
    private String getRuleKey(String category, String checkId) {
        return category + "#" + checkId;
    }
}
