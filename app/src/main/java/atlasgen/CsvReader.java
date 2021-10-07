/*
 *  This file is part of Empty3.
 *
 *     Empty3 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Empty3 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Empty3.  If not, see <https://www.gnu.org/licenses/>. 2
 */

/*
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>
 */

package atlasgen; /*__
 * Created by manushell on 19-06-18.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    private final int countLineModulo = 10000;
    private final int MAX_LINE_LENGTH_2 = 5000;
    private boolean fileContainsFieldDescription;
    private String csvFieldSeparator;
    private File csvFile;
    private String csvLineSeparator;
    private List<String> columns = new ArrayList<>();
    private Action action;

    public CsvReader(File csvFile, String csvFieldSeparator, String csvLineSeparator, boolean fileContainsFieldDescription) {
        this.csvFile = csvFile;
        this.csvFieldSeparator = csvFieldSeparator;
        this.csvLineSeparator = csvLineSeparator;
        this.fileContainsFieldDescription = fileContainsFieldDescription;
    }

    public void process() {
        boolean firstLine = true;
        int nRead = 0;

        ArrayList<String[]> lignesRestantes = new ArrayList<>();
        ArrayList<String[]> colonnesRestantes = new ArrayList<>();
        ArrayList<String[]> morceauxRestants = new ArrayList<>();


        String reste = "";

        try {
            FileInputStream fileInputStream = null;
            fileInputStream = new FileInputStream(csvFile);
            byte[] bytes = new byte[MAX_LINE_LENGTH_2];
            int countLines = 0;

            while ((nRead = fileInputStream.read(bytes)) > 0 /*|| reste.toString().length()>0*/) {
                if (countLines % countLineModulo == 0)
                    ;//System.out.println(""+countLines+" lignes traitées");
                String read = "";
                for (int i = 0; i < nRead; i++)
                    read += (char) (bytes[i]);
                read = "" + reste.toString() + read;
                reste = "";

                if (firstLine && fileContainsFieldDescription) {
                    String[] split0 = read.split(csvLineSeparator);
                    String[] split1 = split0[0].split(csvFieldSeparator);
                    for (int i = 0; i < split1.length; i++) {
                        columns.add(split1[i]);
                    }
                    firstLine = false;
                    // Traitement terminé.
                    // Reprendre ce qu'il reste pour la loop.
                    reste += split0[split0.length - 1];

                } else {
                    String[] split0 = read.split(csvLineSeparator);
                    for (int j = 0; j < split0.length - 1; j++) {
                        String[] split1 = split0[0].split(csvFieldSeparator);


                        // Traitement terminé. On passe à l'action
                        action.processLine(new CsvLine(columns, split1));
                        countLines++;
                    }
                    // Reprendre ce qu'il reste pour la loop.

                    reste += split0[split0.length - 1];
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public int getMAX_LINE_LENGTH_2() {
        return MAX_LINE_LENGTH_2;
    }


    public boolean isFileContainsFieldDescription() {
        return fileContainsFieldDescription;
    }

    public void setFileContainsFieldDescription(boolean fileContainsFieldDescription) {
        this.fileContainsFieldDescription = fileContainsFieldDescription;
    }

    public String getCsvFieldSeparator() {
        return csvFieldSeparator;
    }

    public void setCsvFieldSeparator(String csvFieldSeparator) {
        this.csvFieldSeparator = csvFieldSeparator;
    }

    public File getCsvFile() {
        return csvFile;
    }

    public void setCsvFile(File csvFile) {
        this.csvFile = csvFile;
    }

    public String getCsvLineSeparator() {
        return csvLineSeparator;
    }

    public void setCsvLineSeparator(String csvLineSeparator) {
        this.csvLineSeparator = csvLineSeparator;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}

