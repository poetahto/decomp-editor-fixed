package me.disturbo.data.parsers;

import me.disturbo.data.LineParser;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class MovesParser implements LineParser<LinkedHashMap<String, String>> {

    @Override
    public boolean parseLine(LinkedHashMap<String, String> moves, String line) {
        if(line.contains("[MOVE_") && !line.contains("[MOVE_NAME_LENGTH")) {
            line = line.replaceAll("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)", "");

            String name = null;
            String declaration = null;

            { // Find the declaration of the move: given [MOVE_DECLARATION], extract MOVE_DECLARATION
                int startIndex = line.indexOf("[");
                int endIndex = line.indexOf("]");

                if (startIndex != -1 && endIndex != -1) {
                    declaration = line.substring(1, endIndex);
                }
            }

            { // Find the name of the move
                int startIndex = line.indexOf("_(\"");
                int endIndex = line.indexOf("\")");

                if (startIndex != -1 && endIndex != -1) {
                    // We add three to skip the initial characters, we only want the middle.
                    // E.g: given _("MOVE_NAME"), extract MOVE_NAME.
                    name = line.substring(startIndex + 3, endIndex);
                }
            }

            if (declaration != null && name != null) {
                moves.put(declaration, name);
            }
        }
        return false;
    }
}
