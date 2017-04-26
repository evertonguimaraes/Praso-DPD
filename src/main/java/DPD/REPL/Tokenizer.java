package DPD.REPL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Justice on 1/10/2017.
 */
public class Tokenizer {

    String[] tokens;
    int index;

    public Tokenizer(String str, String delim) {
        List<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        while(str.length() > 0) {
            char c = str.charAt(0);
            str = str.substring(1);
            if( delim.contains(String.valueOf(c) ) ) {
                if(sb.length() > 0)
                    tokens.add(sb.toString());
                sb = new StringBuilder();
            }
            else if(c == '\'') {
                int cutoff = str.indexOf('\'');
                if( cutoff != -1 ) {
                    String token = str.substring(0, cutoff);
                    sb = new StringBuilder();
                    tokens.add(token);
                    str = str.substring(cutoff);
                }
            }
            else {
                sb.append(c);
            }
        }

        // add any remaining token
        if(sb.length() > 0)
            tokens.add(sb.toString());
        this.tokens = new String[tokens.size()];
        this.tokens = tokens.toArray(this.tokens);
    }

    public String nextToken() {
        try {
            return tokens[index++];
        }catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("More tokens expected");
        }
    }

    public String[] rest() {
        return Arrays.copyOfRange(tokens, index, tokens.length); 
    }
}