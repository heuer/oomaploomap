/* The following code was generated by JFlex 1.4.3 on 13.05.11 12:26 */

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

/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 13.05.11 12:26 from the specification file
 * <tt>E:/projekte/oomaploomap/oomaploomap/src/grammar/TomaTokenMaker.flex</tt>
 */
public class TomaTokenMaker extends AbstractJFlexTokenMaker {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0, 0
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\2\1\1\1\0\1\2\1\1\22\0\1\2\1\42\1\0"+
    "\1\13\1\5\2\0\1\7\2\6\1\47\1\11\1\6\1\10\1\6"+
    "\1\0\12\12\1\0\1\6\1\44\1\43\1\45\1\0\1\6\1\31"+
    "\1\35\1\17\1\32\1\15\1\34\1\41\1\22\1\27\2\3\1\16"+
    "\1\33\1\24\1\25\1\37\1\3\1\23\1\14\1\20\1\30\1\40"+
    "\1\21\1\26\1\36\1\3\1\6\1\0\1\6\1\0\1\4\1\0"+
    "\1\31\1\35\1\17\1\32\1\15\1\34\1\41\1\22\1\27\2\3"+
    "\1\16\1\33\1\24\1\25\1\37\1\3\1\23\1\14\1\20\1\30"+
    "\1\40\1\21\1\26\1\36\1\3\1\0\1\50\1\0\1\46\uff81\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\1\2\1\3\1\1\1\4\3\1\1\5"+
    "\1\6\21\3\1\1\3\7\1\1\2\10\1\0\1\11"+
    "\1\3\1\12\20\3\1\12\20\3\1\13\1\12\60\3";

  private static int [] zzUnpackAction() {
    int [] result = new int[122];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\51\0\122\0\173\0\244\0\51\0\315\0\366"+
    "\0\u011f\0\u011f\0\u0148\0\u0171\0\u019a\0\u01c3\0\u01ec\0\u0215"+
    "\0\u023e\0\u0267\0\u0290\0\u02b9\0\u02e2\0\u030b\0\u0334\0\u035d"+
    "\0\u0386\0\u03af\0\u03d8\0\u0401\0\u042a\0\51\0\u0453\0\u047c"+
    "\0\u04a5\0\u04ce\0\51\0\315\0\51\0\u04f7\0\173\0\u0520"+
    "\0\u0549\0\u0572\0\u059b\0\u05c4\0\u05ed\0\u0616\0\u063f\0\u0668"+
    "\0\u0691\0\u06ba\0\u06e3\0\u070c\0\u0735\0\u075e\0\u0787\0\u07b0"+
    "\0\u07d9\0\u0802\0\u082b\0\u0854\0\u087d\0\u08a6\0\u08cf\0\u08f8"+
    "\0\u0921\0\u094a\0\u0973\0\u099c\0\u09c5\0\u09ee\0\u0a17\0\u0a40"+
    "\0\173\0\u0a69\0\u0a92\0\u0abb\0\u0ae4\0\u0b0d\0\u0b36\0\u0b5f"+
    "\0\u0b88\0\u0bb1\0\u0bda\0\u0c03\0\u0c2c\0\u0c55\0\u0c7e\0\u0ca7"+
    "\0\u0cd0\0\u0cf9\0\u0d22\0\u0d4b\0\u0d74\0\u0d9d\0\u0dc6\0\u0def"+
    "\0\u0e18\0\u0e41\0\u0e6a\0\u0e93\0\u0ebc\0\u0ee5\0\u0f0e\0\u0f37"+
    "\0\u0f60\0\u0f89\0\u0fb2\0\u0fdb\0\u1004\0\u102d\0\u1056\0\u107f"+
    "\0\u10a8\0\u10d1\0\u10fa\0\u1123\0\u114c\0\u1175\0\u119e\0\u11c7"+
    "\0\u11f0\0\u1219";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[122];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\2\2\3\1\4\1\2\1\5\1\6\1\7\1\10"+
    "\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20"+
    "\1\21\1\4\1\22\1\23\1\24\1\4\1\25\1\26"+
    "\1\27\1\30\1\31\1\4\1\32\1\4\1\33\1\34"+
    "\1\4\1\35\1\36\2\37\1\40\1\36\1\41\52\0"+
    "\2\3\51\0\2\4\3\0\1\4\1\0\1\4\1\0"+
    "\26\4\12\0\1\42\1\0\1\43\6\0\26\42\7\0"+
    "\7\44\1\45\41\44\12\0\1\12\32\0\1\36\15\0"+
    "\1\12\36\0\1\13\1\0\47\13\3\0\2\4\3\0"+
    "\1\4\1\0\1\4\1\0\1\4\1\46\2\47\7\4"+
    "\1\47\1\50\11\4\12\0\2\4\3\0\1\4\1\0"+
    "\1\4\1\0\12\4\1\51\13\4\12\0\2\4\3\0"+
    "\1\4\1\0\1\4\1\0\1\4\1\52\7\4\1\53"+
    "\1\4\1\54\12\4\12\0\2\4\3\0\1\4\1\0"+
    "\1\4\1\0\11\4\1\55\14\4\12\0\2\4\3\0"+
    "\1\4\1\0\1\4\1\0\7\4\1\56\1\4\1\57"+
    "\1\4\1\60\6\4\1\61\3\4\12\0\2\4\3\0"+
    "\1\4\1\0\1\4\1\0\6\4\1\62\17\4\12\0"+
    "\2\4\3\0\1\4\1\0\1\4\1\0\1\4\1\63"+
    "\7\4\1\64\14\4\12\0\2\4\3\0\1\4\1\0"+
    "\1\4\1\0\11\4\1\65\2\4\1\66\1\67\10\4"+
    "\12\0\2\4\3\0\1\4\1\0\1\4\1\0\3\4"+
    "\1\47\3\4\1\70\10\4\1\71\5\4\12\0\2\4"+
    "\3\0\1\4\1\0\1\4\1\0\1\47\7\4\1\72"+
    "\5\4\1\47\7\4\12\0\2\4\3\0\1\4\1\0"+
    "\1\4\1\0\10\4\1\73\12\4\1\74\2\4\12\0"+
    "\2\4\3\0\1\4\1\0\1\4\1\0\1\75\1\4"+
    "\1\76\5\4\1\77\13\4\1\100\1\4\12\0\2\4"+
    "\3\0\1\4\1\0\1\4\1\0\1\4\1\101\11\4"+
    "\1\102\1\4\1\103\10\4\12\0\2\4\3\0\1\4"+
    "\1\0\1\4\1\0\13\4\1\104\1\4\1\105\10\4"+
    "\12\0\2\4\3\0\1\4\1\0\1\4\1\0\22\4"+
    "\1\47\3\4\12\0\2\4\3\0\1\4\1\0\1\4"+
    "\1\0\2\4\1\106\23\4\12\0\2\4\3\0\1\4"+
    "\1\0\1\4\1\0\15\4\1\107\10\4\52\0\1\36"+
    "\2\0\1\40\45\0\1\36\54\0\1\36\51\0\1\36"+
    "\3\0\2\42\3\0\1\42\1\0\1\42\1\0\26\42"+
    "\12\0\2\4\3\0\1\4\1\0\1\4\1\0\2\4"+
    "\1\110\23\4\12\0\2\4\3\0\1\4\1\0\1\4"+
    "\1\0\17\4\1\111\1\4\1\112\1\4\1\113\2\4"+
    "\12\0\2\4\3\0\1\4\1\0\1\4\1\0\3\4"+
    "\1\114\7\4\1\115\12\4\12\0\2\4\3\0\1\4"+
    "\1\0\1\4\1\0\10\4\1\116\15\4\12\0\2\4"+
    "\3\0\1\4\1\0\1\4\1\0\5\4\1\117\20\4"+
    "\12\0\2\4\3\0\1\4\1\0\1\4\1\0\17\4"+
    "\1\120\6\4\12\0\2\4\3\0\1\4\1\0\1\4"+
    "\1\0\10\4\1\121\3\4\1\122\11\4\12\0\2\4"+
    "\3\0\1\4\1\0\1\4\1\0\13\4\1\123\12\4"+
    "\12\0\1\4\1\124\3\0\1\4\1\0\1\4\1\0"+
    "\26\4\12\0\2\4\3\0\1\4\1\0\1\4\1\0"+
    "\4\4\1\125\21\4\12\0\2\4\3\0\1\4\1\0"+
    "\1\4\1\0\23\4\1\126\2\4\12\0\2\4\3\0"+
    "\1\4\1\0\1\4\1\0\1\4\1\127\24\4\12\0"+
    "\2\4\3\0\1\4\1\0\1\4\1\0\13\4\1\130"+
    "\4\4\1\47\5\4\12\0\2\4\3\0\1\4\1\0"+
    "\1\4\1\0\2\4\1\126\23\4\12\0\2\4\3\0"+
    "\1\4\1\0\1\4\1\0\4\4\1\47\21\4\12\0"+
    "\2\4\3\0\1\4\1\0\1\4\1\0\2\4\1\76"+
    "\23\4\12\0\2\4\3\0\1\4\1\0\1\4\1\0"+
    "\17\4\1\126\6\4\12\0\2\4\3\0\1\4\1\0"+
    "\1\4\1\0\16\4\1\113\7\4\12\0\2\4\3\0"+
    "\1\4\1\0\1\4\1\0\20\4\1\131\5\4\12\0"+
    "\2\4\3\0\1\4\1\0\1\4\1\0\1\132\3\4"+
    "\1\133\21\4\12\0\2\4\3\0\1\4\1\0\1\4"+
    "\1\0\13\4\1\134\12\4\12\0\2\4\3\0\1\4"+
    "\1\0\1\4\1\0\23\4\1\117\2\4\12\0\2\4"+
    "\3\0\1\4\1\0\1\4\1\0\3\4\1\47\22\4"+
    "\12\0\2\4\3\0\1\4\1\0\1\4\1\0\2\4"+
    "\1\47\23\4\12\0\2\4\3\0\1\4\1\0\1\4"+
    "\1\0\16\4\1\47\7\4\12\0\2\4\3\0\1\4"+
    "\1\0\1\4\1\0\25\4\1\111\12\0\2\4\3\0"+
    "\1\4\1\0\1\4\1\0\1\75\25\4\12\0\2\4"+
    "\3\0\1\4\1\0\1\4\1\0\1\135\25\4\12\0"+
    "\2\4\3\0\1\4\1\0\1\4\1\0\4\4\1\136"+
    "\21\4\12\0\2\4\3\0\1\4\1\0\1\4\1\0"+
    "\10\4\1\111\15\4\12\0\2\4\3\0\1\4\1\0"+
    "\1\4\1\0\12\4\1\111\13\4\12\0\2\4\3\0"+
    "\1\4\1\0\1\4\1\0\15\4\1\137\10\4\12\0"+
    "\2\4\3\0\1\4\1\0\1\4\1\0\7\4\1\47"+
    "\16\4\12\0\2\4\3\0\1\4\1\0\1\4\1\0"+
    "\1\4\1\140\24\4\12\0\2\4\3\0\1\4\1\0"+
    "\1\4\1\0\1\141\25\4\12\0\2\4\3\0\1\4"+
    "\1\0\1\4\1\0\1\4\1\107\24\4\12\0\2\4"+
    "\3\0\1\4\1\0\1\4\1\0\1\4\1\142\24\4"+
    "\12\0\2\4\3\0\1\4\1\0\1\4\1\0\1\143"+
    "\25\4\12\0\2\4\3\0\1\4\1\0\1\4\1\0"+
    "\25\4\1\144\12\0\2\4\3\0\1\4\1\0\1\4"+
    "\1\0\1\4\1\145\24\4\12\0\2\4\3\0\1\4"+
    "\1\0\1\4\1\0\13\4\1\65\12\4\12\0\2\4"+
    "\3\0\1\4\1\0\1\4\1\0\3\4\1\146\22\4"+
    "\12\0\2\4\3\0\1\4\1\0\1\4\1\0\10\4"+
    "\1\147\15\4\12\0\2\4\3\0\1\4\1\0\1\4"+
    "\1\0\17\4\1\111\6\4\12\0\2\4\3\0\1\4"+
    "\1\0\1\4\1\0\10\4\1\150\15\4\12\0\2\4"+
    "\3\0\1\4\1\0\1\4\1\0\2\4\1\151\23\4"+
    "\12\0\2\4\3\0\1\4\1\0\1\4\1\0\1\4"+
    "\1\47\24\4\12\0\2\4\3\0\1\4\1\0\1\4"+
    "\1\0\7\4\1\126\16\4\12\0\2\4\3\0\1\4"+
    "\1\0\1\4\1\0\20\4\1\152\5\4\12\0\2\4"+
    "\3\0\1\4\1\0\1\4\1\0\1\153\25\4\12\0"+
    "\2\4\3\0\1\4\1\0\1\4\1\0\4\4\1\154"+
    "\21\4\12\0\2\4\3\0\1\4\1\0\1\4\1\0"+
    "\1\4\1\155\24\4\12\0\2\4\3\0\1\4\1\0"+
    "\1\4\1\0\11\4\1\156\14\4\12\0\2\4\3\0"+
    "\1\4\1\0\1\4\1\0\4\4\1\157\21\4\12\0"+
    "\2\4\3\0\1\4\1\0\1\4\1\0\15\4\1\47"+
    "\10\4\12\0\2\4\3\0\1\4\1\0\1\4\1\0"+
    "\22\4\1\113\3\4\12\0\2\4\3\0\1\4\1\0"+
    "\1\4\1\0\3\4\1\65\22\4\12\0\2\4\3\0"+
    "\1\4\1\0\1\4\1\0\4\4\1\160\21\4\12\0"+
    "\2\4\3\0\1\4\1\0\1\4\1\0\23\4\1\65"+
    "\2\4\12\0\2\4\3\0\1\4\1\0\1\4\1\0"+
    "\4\4\1\161\21\4\12\0\2\4\3\0\1\4\1\0"+
    "\1\4\1\0\4\4\1\162\21\4\12\0\2\4\3\0"+
    "\1\4\1\0\1\4\1\0\7\4\1\163\16\4\12\0"+
    "\2\4\3\0\1\4\1\0\1\4\1\0\15\4\1\147"+
    "\10\4\12\0\2\4\3\0\1\4\1\0\1\4\1\0"+
    "\4\4\1\111\21\4\12\0\2\4\3\0\1\4\1\0"+
    "\1\4\1\0\14\4\1\123\11\4\12\0\2\4\3\0"+
    "\1\4\1\0\1\4\1\0\1\4\1\163\24\4\12\0"+
    "\2\4\3\0\1\4\1\0\1\4\1\0\13\4\1\113"+
    "\12\4\12\0\2\4\3\0\1\4\1\0\1\4\1\0"+
    "\1\4\1\65\24\4\12\0\2\4\3\0\1\4\1\0"+
    "\1\4\1\0\15\4\1\164\10\4\12\0\2\4\3\0"+
    "\1\4\1\0\1\4\1\0\7\4\1\165\16\4\12\0"+
    "\2\4\3\0\1\4\1\0\1\4\1\0\10\4\1\47"+
    "\15\4\12\0\2\4\3\0\1\4\1\0\1\4\1\0"+
    "\13\4\1\166\12\4\12\0\2\4\3\0\1\4\1\0"+
    "\1\4\1\0\7\4\1\111\16\4\12\0\2\4\3\0"+
    "\1\4\1\0\1\4\1\0\1\47\25\4\12\0\2\4"+
    "\3\0\1\4\1\0\1\4\1\0\6\4\1\111\17\4"+
    "\12\0\2\4\3\0\1\4\1\0\1\4\1\0\3\4"+
    "\1\167\22\4\12\0\2\4\3\0\1\4\1\0\1\4"+
    "\1\0\10\4\1\170\15\4\12\0\2\4\3\0\1\4"+
    "\1\0\1\4\1\0\1\110\25\4\12\0\2\4\3\0"+
    "\1\4\1\0\1\4\1\0\10\4\1\140\15\4\12\0"+
    "\2\4\3\0\1\4\1\0\1\4\1\0\15\4\1\171"+
    "\10\4\12\0\2\4\3\0\1\4\1\0\1\4\1\0"+
    "\3\4\1\126\22\4\12\0\2\4\3\0\1\4\1\0"+
    "\1\4\1\0\1\172\25\4\12\0\2\4\3\0\1\4"+
    "\1\0\1\4\1\0\1\4\1\111\24\4\7\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[4674];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\11\3\1\1\11\27\1\1\11\4\1\1\11"+
    "\1\0\1\11\125\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[122];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
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



  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public TomaTokenMaker(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public TomaTokenMaker(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 180) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public org.fife.ui.rsyntaxtextarea.Token yylex() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 10: 
          { addToken(Token.RESERVED_WORD);
          }
        case 12: break;
        case 3: 
          { addToken(Token.IDENTIFIER);
          }
        case 13: break;
        case 11: 
          { addToken(Token.FUNCTION);
          }
        case 14: break;
        case 9: 
          { addToken(Token.LITERAL_STRING_DOUBLE_QUOTE);
          }
        case 15: break;
        case 2: 
          { addToken(Token.WHITESPACE);
          }
        case 16: break;
        case 8: 
          { addToken(Token.VARIABLE);
          }
        case 17: break;
        case 6: 
          { addToken(Token.COMMENT_EOL);
          }
        case 18: break;
        case 1: 
          { addToken(Token.ERROR_IDENTIFIER);
          }
        case 19: break;
        case 7: 
          { addToken(Token.OPERATOR);
          }
        case 20: break;
        case 5: 
          { addToken(Token.LITERAL_NUMBER_DECIMAL_INT);
          }
        case 21: break;
        case 4: 
          { addToken(Token.SEPARATOR);
          }
        case 22: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
              {
                addNullToken(); return firstToken;
              }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
