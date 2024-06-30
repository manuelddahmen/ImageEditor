/*
 * Copyright (c) 2024.
 *
 *
 *  Copyright 2023 Manuel Daniel Dahmen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package one.empty3.feature0;

public abstract class FilterMatPixM {
/*
    public int getCompNo() {
        return compNo;
    }

    public void setCompNo(int compNo) {
        this.compNo = compNo;
    }
*/

    public abstract M3 filter(M3 original);

    public abstract void element(M3 source, M3 copy, int i, int j, int ii, int ij);

    public abstract M3 norm(M3 m3, M3 copy);
}
