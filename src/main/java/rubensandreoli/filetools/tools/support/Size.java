/*
 * Copyright (C) 2020 Rubens A. Andreoli Jr.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package rubensandreoli.filetools.tools.support;

import java.text.DecimalFormat;

public class Size implements Comparable<Size>{
           
    private final long bytes;
    private final String text;

    public Size(long size) {
        this.bytes = size;
        this.text = formatSize(size);
    }

    @Override
    public int compareTo(Size s) {
        return Long.compare(this.bytes, s.bytes);
    }

    @Override
    public String toString() {
        return text;
    }
    
    public static String formatSize(long bytes){
        if(bytes <= 0) return "0";
        final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(bytes)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(bytes/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

}
