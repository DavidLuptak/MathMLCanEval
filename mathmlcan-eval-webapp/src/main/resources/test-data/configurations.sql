/*
 * Copyright 2019 the original author or authors.
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
insert into configurations (id, content, note, name, visible_to_public, user_id) values (1, '<?xml version="1.0" encoding="UTF-8"?>
<config>
  <module name="ElementMinimizer">
    <property name="keepAttributes.mfrac">linethickness=0 linethickness=1</property>
  </module>

  <module name="ScriptNormalizer" />
  <module name="MrowNormalizer" />
  <!--    <module name="MfencedReplacer" />-->
  <module name="OperatorNormalizer">
    <property name="removeempty">true</property>
  </module>
</config>
', 'missing element...', 'no content-v2.3', true, 1),

(2,'<?xml version="1.0" encoding="UTF-8"?>
<config>
  <module name="ElementMinimizer">
    <property name="keepAttributes.mfrac">linethickness=0 linethickness=1</property>
  </module>
</config>
','should work since 1.2', 'defautl config 1.2', false, 1)
