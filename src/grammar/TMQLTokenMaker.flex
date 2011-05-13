/*
 * Copyright 2011 Lars Heuer (heuer[at]semagia.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.semagia.ooloo.mode;

import java.io.IOException;

import javax.swing.text.Segment;

import org.fife.ui.rsyntaxtextarea.AbstractJFlexTokenMaker;
import org.fife.ui.rsyntaxtextarea.DefaultToken;
import org.fife.ui.rsyntaxtextarea.Token;

/**
 * TMQL tokenizer.
 * 
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 */
@SuppressWarnings("unused")
%%

%public
%class TMQLTokenMaker
%unicode
%extends AbstractJFlexTokenMaker
%caseless
%pack
%type org.fife.ui.rsyntaxtextarea.Token

%{
    public TMQLTokenMaker() {
        super();
    }

    /* (non-Javadoc)
     * @see org.fife.ui.rsyntaxtextarea.TokenMakerBase#getLineCommentStartAndEnd()
     */
    public String[] getLineCommentStartAndEnd() {
        return new String[] {"#", null };
    }

    public Token getTokenList(Segment text, int initialTokenType, int startOffset) {
        resetTokenList();
        this.offsetShift = -text.offset + startOffset;
        // Start off in the proper state.
        int state = Token.NULL;
        s = text;
        try {
          yyreset(zzReader);
          yybegin(state);
          return yylex();
        } catch (IOException ioe) {
          ioe.printStackTrace();
          return new DefaultToken();
        }
    }

    /**
     * Adds the token specified to the current linked list of tokens.
     *
     * @param tokenType The token's type.
     */
    private void addToken(int tokenType) {
        addToken(zzStartRead, zzMarkedPos-1, tokenType);
    }

    /**
     * Adds the token specified to the current linked list of tokens.
     *
     * @param tokenType The token's type.
     * @see #addHyperlinkToken(int, int, int)
     */
    private void addToken(int start, int end, int tokenType) {
        int so = start + offsetShift;
        addToken(zzBuffer, start,end, tokenType, so, false);
    }

    /**
     * Adds the token specified to the current linked list of tokens.
     *
     * @param array The character array.
     * @param start The starting offset in the array.
     * @param end The ending offset in the array.
     * @param tokenType The token's type.
     * @param startOffset The offset in the document at which this token
     *                    occurs.
     * @param hyperlink Whether this token is a hyperlink.
     */
    public void addToken(char[] array, int start, int end, int tokenType,
              int startOffset, boolean hyperlink) {
        super.addToken(array, start,end, tokenType, startOffset, hyperlink);
        zzStartRead = zzMarkedPos;
    }

    /**
     * Refills the input buffer.
     *
     * @return      <code>true</code> if EOF was reached, otherwise
     *              <code>false</code>.
     * @exception   IOException  if any I/O-Error occurs.
     */
    private /*keep*/ boolean zzRefill() throws java.io.IOException {
        return zzCurrentPos>=s.offset+s.count;
    }


    /**
     * Resets the scanner to read from a new input stream.
     * Does not close the old reader.
     *
     * All internal variables are reset, the old input stream 
     * <b>cannot</b> be reused (internal buffer is discarded and lost).
     * Lexical state is set to <tt>YY_INITIAL</tt>.
     *
     * @param reader   the new input stream 
     */
    public /*keep*/ final void yyreset(java.io.Reader reader) throws java.io.IOException {
        // 's' has been updated.
        zzBuffer = s.array;
        /*
         * We replaced the line below with the two below it because zzRefill
         * no longer "refills" the buffer (since the way we do it, it's always
         * "full" the first time through, since it points to the segment's
         * array).  So, we assign zzEndRead here.
         */
        //zzStartRead = zzEndRead = s.offset;
        zzStartRead = s.offset;
        zzEndRead = zzStartRead + s.count - 1;
        zzCurrentPos = zzMarkedPos = s.offset;
        zzLexicalState = YYINITIAL;
        zzReader = reader;
        zzAtBOL  = true;
        zzAtEOF  = false;
    }

%}

LineTerminator  = \r|\n|\r\n
Whitespace      = {LineTerminator} | [ \t\f]

IdentifierStart = [a-zA-Z_] | [\u00C0-\u00D6] | [\u00D8-\u00F6] 
                            | [\u00F8-\u02FF] | [\u0370-\u037D] 
                            | [\u037F-\u1FFF] | [\u200C-\u200D] 
                            | [\u2070-\u218F] | [\u2C00-\u2FEF] 
                            | [\u3001-\uD7FF] | [\uF900-\uFDCF] 
                            | [\uFDF0-\uFFFD] 
                            //| [\u10000-\uEFFFF]
IdentifierChar  = {IdentifierStart} | [\-\.0-9] | \u00B7 | [\u0300-\u036F] 
                                                | [\u203F-\u2040]
Identifier      = {IdentifierStart}{IdentifierChar}*
Variable        = "$"{Identifier}

Separator       = [@\^&\|\*\-\+=\(\)\{\}\[\]\"'\/\\<>:\.,\~]

QName           = {Identifier}":"([0-9]|{IdentifierStart}){IdentifierChar}*

String          = \"([^\"]|\"\")*\"
IRI             = "<"[^<>\"\{\}\`\\ ]+">"
Integer         = ("-" | "+")? [0-9]+
Decimal         = ("-" | "+")? ( [0-9]+ \. [0-9]+ | \. ([0-9])+ )
Date            = ("-" | "+")? [0-9]{4} [0-9]* "-" (0 [1-9] | 1 [0-2]) "-" (0 [1-9] | 1 [0-9] | 2 [0-9] | 3 [0-1])
Time            = [0-9]{2} : [0-9]{2} : [0-9]{2} (\.[0-9]+)? ({TimeZone})?
TimeZone        = Z | ( ( "+" | "-" ) [0-9]{2} : [0-9]{2} )
DateTime        = {Date}"T"{Time}

Comment         = "#"[^\r\n]*

%%

{Whitespace}+           { addToken(Token.WHITESPACE); }
{Comment}               { addToken(Token.COMMENT_EOL); }

<YYINITIAL> {
    // Keywords
    "%prefix"
    | "%pragma"
    | "select"
    | "from"
    | "count"
    | "not"
    | "limit"
    | "offset"
    | "order"
    | "by"
    | "asc"
    | "desc"
    | "unique"
    | "for"
    | "in"
    | "return"
    | "every"
    | "satisfies"
    | "some"
    | "if"
    | "then"
    | "else"
    | "exists"
    | "null"
    | "undef"
    | "at"
    | "least"
    | "most"
    | "every"
    | "iko"
    | "isa"
    // Axes
    | "types"
    | "instances"
    | "supertypes"
    | "subtypes"
    | "players"
    | "roles"
    | "traverse"
    | "characteristics"
    | "scope"
    | "locators"
    | "indicators"
    | "item"
    | "reifier"
    | "atomify"         { addToken(Token.RESERVED_WORD); }

    "true"|"false"      { addToken(Token.LITERAL_BOOLEAN); }

    {QName}             { addToken(Token.IDENTIFIER); }
    {Identifier}        { addToken(Token.IDENTIFIER); }
    {Variable}          { addToken(Token.VARIABLE); }

    // Datatypes
    {String}            { addToken(Token.LITERAL_STRING_DOUBLE_QUOTE); }
//    {IRI}               { return _token(TokenTypes.IRI, 1, 1); }
//    {Date}              { return _token(TokenTypes.DATE); }
//    {DateTime}          { return _token(TokenTypes.DATE_TIME); }
    {Integer}|{Decimal} { addToken(Token.LITERAL_NUMBER_DECIMAL_INT); }

    // Delimiters
    {Separator}         { addToken(Token.SEPARATOR); }

}

<<EOF>>                 { addNullToken(); return firstToken; }

.                       { addToken(Token.ERROR_IDENTIFIER); }
