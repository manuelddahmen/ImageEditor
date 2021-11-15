///*
// *  This file is part of Empty3.
// *
// *     Empty3 is free software: you can redistribute it and/or modify
// *     it under the terms of the GNU General Public License as published by
// *     the Free Software Foundation, either version 3 of the License, or
// *     (at your option) any later version.
// *
// *     Empty3 is distributed in the hope that it will be useful,
// *     but WITHOUT ANY WARRANTY; without even the implied warranty of
// *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// *     GNU General Public License for more details.
// *
// *     You should have received a copy of the GNU General Public License
// *     along with Empty3.  If not, see <https://www.gnu.org/licenses/>. 2
// */
//
///*
// * This program is free software: you can redistribute it and/or modify
// *     it under the terms of the GNU General Public License as published by
// *     the Free Software Foundation, either version 3 of the License, or
// *     (at your option) any later version.
// *
// *     This program is distributed in the hope that it will be useful,
// *     but WITHOUT ANY WARRANTY; without even the implied warranty of
// *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// *     GNU General Public License for more details.
// *
// *     You should have received a copy of the GNU General Public License
// *     along with this program.  If not, see <https://www.gnu.org/licenses/>
// */
//
//package atlasgen;
//
//import one.empty3.feature.app.replace.java.awt.*;
//
///*__
// * Created by manue on 19-06-18.
// */
//public class DrawAction implements Action {
//
//    private Color color;
//    private Pixeler pixeler;
//
//    public DrawAction(Pixeler pixeler, Color color) {
//        this.pixeler = pixeler;
//        this.color = color;
//    }
//
//
//    @Override
//    public void init() {
//
//    }
//
//    @Override
//    public void processLine(CsvLine csvLine) {
//        int lattitudeColumn = 4;
//        int longitudeColumn = 5;
//        String[] lineArray = csvLine.getValue();
//        /*for(int i=0; i<lineArray.length; i++)
//            if(lineArray[i]!=null)
//                System.out.println(""+i+"  "+lineArray[i]);
//        */
//        pixeler.pixelize(
//                (int) ((Double.parseDouble(lineArray[longitudeColumn]) / 180 + 1) / 2 * pixeler.getImage().getWidth()),
//                (int) ((-Double.parseDouble(lineArray[lattitudeColumn]) / 90 + 1) / 2 * pixeler.getImage().getHeight()),
//                color
//        );
//    }
//}
