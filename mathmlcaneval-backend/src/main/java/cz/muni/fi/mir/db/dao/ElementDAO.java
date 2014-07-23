/*
 * Copyright 2014 emptak.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cz.muni.fi.mir.db.dao;

import cz.muni.fi.mir.db.domain.Element;
import java.util.List;

/**
 *
 * @author emptak
 */
public interface ElementDAO
{
    void createElement(Element element);
    void updateElement(Element element);
    void deleteElement(Element element);
    Element getElementByID(Long id);
    Element getElementByName(String name);
    List<Element> getAllElements();
}