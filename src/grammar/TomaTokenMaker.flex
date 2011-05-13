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
 * Toma tokenizer.
 * 
 * @author Lars Heuer (heuer[at]semagia.com) <a href="http://www.semagia.com/">Semagia</a>
 */
@SuppressWarnings("unused")
%%

%public
%class TomaTokenMaker
%unicode
%extends AbstractJFlexTokenMaker
%caseless
%pack
%type org.fife.ui.rsyntaxtextarea.Token

%{
    public TomaTokenMaker() {
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

Identifier      = [A-Za-z][A-Za-z0-9_\-]*
Variable        = "$"{Identifier}

Separator       = [@\(\)\[\];,\.]

String          = '[^\']*'
Integer         = ("-" | "+")? [0-9]+
Decimal         = ("-" | "+")? ( [0-9]+ \. [0-9]+ | \. ([0-9])+ )

Comment         = "#"[^\r\n]*

%%

{Whitespace}+           { addToken(Token.WHITESPACE); }
{Comment}               { addToken(Token.COMMENT_EOL); }

<YYINITIAL> {
    // Keywords
    "select"
    | "where"
    | "not"
    | "exists"
    | "is"
    | "null"
    | "all"
    | "distinct"
    | "limit"
    | "offset"
    | "order"
    | "by"
    | "asc"
    | "desc"
    | "union" 
    | "all"
    | "intersect"
    | "except"
    | "and"
    | "or"
    | "id"
    | "sl"
    | "si"
    | "name"
    | "var"
    | "oc"
    | "ref"
    | "data"
    | "sc"
    | "player"
    | "role"
    | "reifier"
    | "type"
    | "instance"
    | "sub"
    | "super"              { addToken(Token.RESERVED_WORD); }
    
    "count" 
    | "sum" 
    | "max" 
    | "min" 
    | "avg" 
    | "concat"
    | "lowercase" 
    | "uppercase" 
    | "titlecase" 
    | "length" 
    | "substr" 
    | "trim" 
    | "to_num"          { addToken(Token.FUNCTION); }


    "!="
    | "="
    | "<"
    | ">"
    | "<="
    | ">="
    | "~"
    | "~*"
    | "!~"
    | "!~*"
    | "->"
    | "||"
    | "*"               { addToken(Token.OPERATOR); }

    {Identifier}        { addToken(Token.IDENTIFIER); }
    "$$"                { addToken(Token.VARIABLE); }
    {Variable}          { addToken(Token.VARIABLE); }

    // Datatypes
    {String}            { addToken(Token.LITERAL_STRING_DOUBLE_QUOTE); }
    {Integer}           { addToken(Token.LITERAL_NUMBER_DECIMAL_INT); }

    // Delimiters
    {Separator}         { addToken(Token.SEPARATOR); }

}

<<EOF>>                 { addNullToken(); return firstToken; }

.                       { addToken(Token.ERROR_IDENTIFIER); }
