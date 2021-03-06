/*******************************************************************************
* Copyright (c) 2006 Eclipse.org
* 
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
package org.eclipse.gmf.internal.xpand.parser;

public class XpandKWLexerprs implements lpg.lpgjavaruntime.ParseTable, XpandKWLexersym {

    public interface IsKeyword {
        public final static byte isKeyword[] = {0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0
        };
    };
    public final static byte isKeyword[] = IsKeyword.isKeyword;
    public final boolean isKeyword(int index) { return isKeyword[index] != 0; }

    public interface BaseCheck {
        public final static byte baseCheck[] = {0,
            4,4,5,3,3,6,4,7,7,3,
            4,10,6,6,9,7,6,6,10,6,
            9,6,9,6,9,5,6,3,9,2,
            8,7,10,4,7,2,6,4,5,3,
            6,7,6,4,2,7,10
        };
    };
    public final static byte baseCheck[] = BaseCheck.baseCheck;
    public final int baseCheck(int index) { return baseCheck[index]; }
    public final static byte rhs[] = baseCheck;
    public final int rhs(int index) { return rhs[index]; };

    public interface BaseAction {
        public final static char baseAction[] = {
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,1,
            1,1,1,1,1,1,1,1,1,50,
            63,4,50,61,37,35,42,83,78,27,
            55,66,82,87,12,72,88,59,29,47,
            92,91,19,93,96,97,94,98,103,75,
            112,104,107,114,118,117,121,120,122,119,
            127,130,133,134,135,137,136,142,143,145,
            146,148,153,151,155,154,161,163,166,167,
            152,168,175,24,174,179,184,185,186,189,
            190,192,191,196,197,203,194,205,206,210,
            211,213,214,217,222,225,227,228,230,200,
            221,233,235,238,239,246,234,76,250,240,
            248,252,253,254,255,256,262,264,267,258,
            269,271,274,275,276,277,278,281,282,283,
            286,284,293,289,290,170,291,296,295,306,
            300,297,311,315,318,307,319,320,322,324,
            327,328,330,332,334,337,336,340,342,343,
            345,347,352,349,354,355,358,356,357,360,
            362,368,370,372,375,378,377,379,383,386,
            380,387,396,392,382,393,399,402,404,403,
            405,406,407,411,409,410,413,239,239
        };
    };
    public final static char baseAction[] = BaseAction.baseAction;
    public final int baseAction(int index) { return baseAction[index]; }
    public final static char lhs[] = baseAction;
    public final int lhs(int index) { return lhs[index]; };

    public interface TermCheck {
        public final static byte termCheck[] = {0,
            0,1,2,0,4,5,3,7,0,9,
            10,0,12,13,14,15,16,17,0,19,
            20,3,22,0,24,25,0,1,0,18,
            7,31,21,10,0,12,0,3,15,3,
            17,0,16,20,18,11,0,6,2,0,
            22,17,16,12,0,1,15,8,0,10,
            0,1,0,35,18,0,1,2,14,23,
            10,0,38,2,0,0,18,0,37,21,
            6,0,0,8,2,10,0,0,2,2,
            0,0,0,0,20,0,0,0,27,8,
            3,39,0,0,8,28,0,17,16,16,
            15,0,1,0,12,34,0,0,0,0,
            0,0,6,20,4,6,0,14,11,0,
            9,5,0,0,0,0,0,4,20,33,
            4,0,0,9,0,0,5,0,4,14,
            0,0,0,0,0,26,24,5,4,6,
            0,1,0,1,22,0,0,0,1,0,
            23,26,7,0,0,6,10,27,0,1,
            7,30,8,0,0,0,3,2,0,0,
            0,0,8,0,4,0,0,9,7,0,
            5,12,0,7,0,0,4,2,4,0,
            0,2,0,0,21,5,0,0,2,7,
            0,0,9,2,0,1,0,0,1,0,
            4,11,0,0,0,36,7,0,0,0,
            3,3,3,11,10,0,1,0,15,0,
            1,0,0,0,0,0,3,0,11,7,
            5,0,11,0,3,2,0,13,0,3,
            0,14,2,0,0,0,0,0,2,4,
            0,0,0,0,1,0,13,13,0,0,
            0,23,0,16,0,0,0,8,3,0,
            19,19,17,15,12,0,0,17,28,15,
            0,12,16,8,0,9,6,0,0,0,
            6,0,5,0,6,2,0,0,7,0,
            4,0,13,0,5,0,0,4,2,0,
            13,0,0,1,0,6,0,1,0,14,
            6,0,1,0,0,0,0,0,10,0,
            5,0,6,32,10,8,5,0,9,0,
            29,0,5,4,0,1,0,0,0,0,
            9,0,0,30,7,0,0,11,3,11,
            8,0,0,14,8,0,5,2,0,1,
            19,0,0,0,0,0,0,3,0,0,
            0,19,0,12,11,9,6,5,13,0,
            18,0,0,0,0,0,0,0,0,0,
            0,0,0,0,25,0,0,29,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,0,0,
            0,0,0,0,0,0
        };
    };
    public final static byte termCheck[] = TermCheck.termCheck;
    public final int termCheck(int index) { return termCheck[index]; }

    public interface TermAction {
        public final static char termAction[] = {0,
            239,55,58,239,66,68,70,54,239,65,
            56,239,53,64,62,52,61,60,239,59,
            51,107,57,239,67,69,239,84,239,93,
            149,63,94,146,239,148,239,77,147,75,
            145,239,85,144,86,78,239,80,103,239,
            101,76,269,284,239,87,275,72,239,71,
            239,74,239,100,102,239,89,90,88,104,
            73,239,79,95,239,239,98,239,81,99,
            115,239,239,177,82,176,239,239,92,97,
            239,239,239,239,114,239,239,239,96,105,
            112,238,239,239,110,83,239,106,108,111,
            109,239,116,239,113,91,239,239,239,239,
            239,239,121,117,122,279,239,119,120,239,
            123,249,239,239,239,239,239,127,124,118,
            129,239,239,128,239,239,244,239,132,130,
            239,239,239,239,239,125,126,135,136,137,
            239,273,28,138,131,239,239,239,142,239,
            134,133,139,239,239,280,140,243,239,151,
            150,141,143,239,239,239,152,154,239,239,
            239,239,153,239,157,239,239,155,156,239,
            250,283,239,158,239,239,159,246,161,239,
            239,162,239,239,160,163,239,239,166,164,
            239,239,165,241,239,167,239,239,168,239,
            240,171,239,239,38,170,169,239,239,239,
            265,174,179,172,173,239,175,239,278,239,
            178,239,239,239,239,239,183,239,180,182,
            185,239,181,239,186,187,239,184,239,188,
            239,189,191,239,239,239,239,239,242,194,
            239,239,239,239,263,239,192,193,239,239,
            239,190,239,195,239,239,239,200,202,239,
            197,198,199,276,261,239,239,201,196,203,
            239,266,205,204,239,253,206,239,239,239,
            259,239,257,239,282,209,239,239,208,239,
            210,239,207,239,256,239,239,252,212,239,
            211,239,239,285,239,281,239,274,239,213,
            214,239,215,239,239,239,239,239,216,239,
            220,239,221,245,218,219,247,239,248,239,
            271,239,255,222,239,224,239,239,239,239,
            223,239,239,217,225,239,239,226,270,227,
            228,239,239,229,230,239,232,231,239,264,
            233,239,239,239,239,239,239,268,239,239,
            239,234,239,262,260,254,286,258,236,239,
            235,239,239,239,239,239,239,239,239,239,
            239,239,239,239,251,239,239,272
        };
    };
    public final static char termAction[] = TermAction.termAction;
    public final int termAction(int index) { return termAction[index]; }
    public final int asb(int index) { return 0; }
    public final int asr(int index) { return 0; }
    public final int nasb(int index) { return 0; }
    public final int nasr(int index) { return 0; }
    public final int terminalIndex(int index) { return 0; }
    public final int nonterminalIndex(int index) { return 0; }
    public final int scopePrefix(int index) { return 0;}
    public final int scopeSuffix(int index) { return 0;}
    public final int scopeLhs(int index) { return 0;}
    public final int scopeLa(int index) { return 0;}
    public final int scopeStateSet(int index) { return 0;}
    public final int scopeRhs(int index) { return 0;}
    public final int scopeState(int index) { return 0;}
    public final int inSymb(int index) { return 0;}
    public final String name(int index) { return null; }
    public final int getErrorSymbol() { return 0; }
    public final int getScopeUbound() { return 0; }
    public final int getScopeSize() { return 0; }
    public final int getMaxNameLength() { return 0; }

    public final static int
           NUM_STATES        = 188,
           NT_OFFSET         = 53,
           LA_STATE_OFFSET   = 286,
           MAX_LA            = 1,
           NUM_RULES         = 47,
           NUM_NONTERMINALS  = 2,
           NUM_SYMBOLS       = 55,
           SEGMENT_SIZE      = 8192,
           START_STATE       = 48,
           IDENTIFIER_SYMBOL = 0,
           EOFT_SYMBOL       = 39,
           EOLT_SYMBOL       = 54,
           ACCEPT_ACTION     = 238,
           ERROR_ACTION      = 239;

    public final static boolean BACKTRACK = false;

    public final int getNumStates() { return NUM_STATES; }
    public final int getNtOffset() { return NT_OFFSET; }
    public final int getLaStateOffset() { return LA_STATE_OFFSET; }
    public final int getMaxLa() { return MAX_LA; }
    public final int getNumRules() { return NUM_RULES; }
    public final int getNumNonterminals() { return NUM_NONTERMINALS; }
    public final int getNumSymbols() { return NUM_SYMBOLS; }
    public final int getSegmentSize() { return SEGMENT_SIZE; }
    public final int getStartState() { return START_STATE; }
    public final int getStartSymbol() { return lhs[0]; }
    public final int getIdentifierSymbol() { return IDENTIFIER_SYMBOL; }
    public final int getEoftSymbol() { return EOFT_SYMBOL; }
    public final int getEoltSymbol() { return EOLT_SYMBOL; }
    public final int getAcceptAction() { return ACCEPT_ACTION; }
    public final int getErrorAction() { return ERROR_ACTION; }
    public final boolean isValidForParser() { return isValidForParser; }
    public final boolean getBacktrack() { return BACKTRACK; }

    public final int originalState(int state) { return 0; }
    public final int asi(int state) { return 0; }
    public final int nasi(int state) { return 0; }
    public final int inSymbol(int state) { return 0; }

    public final int ntAction(int state, int sym) {
        return baseAction[state + sym];
    }

    public final int tAction(int state, int sym) {
        int i = baseAction[state],
            k = i + sym;
        return termAction[termCheck[k] == sym ? k : i];
    }
    public final int lookAhead(int la_state, int sym) {
        int k = la_state + sym;
        return termAction[termCheck[k] == sym ? k : la_state];
    }
}
