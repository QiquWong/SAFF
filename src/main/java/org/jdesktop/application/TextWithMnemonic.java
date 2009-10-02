package org.jdesktop.application;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Gratiously donated by Escenic.
 * Orignal author: Yngvar So&ring;rensen
 *
 * @author <a href="mailto:erlend@hamnaberg.net">Erlend Hamnaberg</a>
 * @version $Id: //depot/escenic/studio/osl/trunk/studio-swing/src/main/java/com/escenic/swing/TextWithMnemonic.java#1 $
 */
class TextWithMnemonic {

    // First part is a zero-width negative look-behind non-capturing group of '&'
    // Last part is any character or digit or end-of-line
    // See http://java.sun.com/j2se/1.4.2/docs/api/java/util/regex/Pattern.html#sum
    private static final Pattern MNEMONIC_PATTERN = Pattern.compile("(?<!&)\\&(\\p{L}|\\d|$)");

    private final String textWithMnemonic;
    private final String textWithoutMnemonic;
    private final Integer mnemonic;
    private Integer mIndex;

    /**
     * Creates a text with mnemonic.
     * <p/>
     * The mnemonic character is specified with an ampersand (&amp;) character.
     * To insert a literal &amp;, escape it with a double ampersand (&amp;&amp;).
     *
     * @param pTextWithMnemonic the text with mnemonic.
     */
    public TextWithMnemonic(final String pTextWithMnemonic) {
        textWithMnemonic = pTextWithMnemonic;
        Matcher matcher = MNEMONIC_PATTERN.matcher(pTextWithMnemonic);

        if (matcher.find()) {
            String mnemonicCharacter = matcher.group(1);
            if (mnemonicCharacter.length() == 0) {
                throw new IllegalArgumentException("Missing mnemonic character: " + pTextWithMnemonic);
            }
            mIndex = matcher.start(1) - 1;
            mnemonic = (int) Character.toUpperCase(mnemonicCharacter.charAt(0));

            if (matcher.find()) {
                throw new IllegalArgumentException("Only one mnemonic per string allowed: " + pTextWithMnemonic);
            }
        }
        else {
            mnemonic = null;
            mIndex = null;
        }

        String temp = matcher.replaceAll("$1");

        // Fix mnemonic index if we hae any escapes before the mnemonic
        if (mIndex != null) {
            int index = -1;
            while ((index = temp.indexOf("&&", index + 1)) >= 0) {
                if (index < mIndex) {
                    mIndex = mIndex - 1;
                }
            }
        }

        textWithoutMnemonic = temp.replaceAll("&&", "&");
    }

    public String getTextWithMnemonic() {
        return textWithMnemonic;
    }

    public String getTextWithoutMnemonic() {
        return textWithoutMnemonic;
    }

    public Integer getMnemonic() {
        return mnemonic;
    }

    public Integer getIndex() {
        return mIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TextWithMnemonic that = (TextWithMnemonic) o;

        if (textWithMnemonic != null ? !textWithMnemonic.equals(that.textWithMnemonic) : that.textWithMnemonic != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return 31 * (textWithMnemonic != null ? textWithMnemonic.hashCode() : 0);
    }

    @Override
    public String toString() {
        return String.format("menmonic: %s, index: %s, orignal: %s", mnemonic, mIndex, textWithMnemonic);
    }
}