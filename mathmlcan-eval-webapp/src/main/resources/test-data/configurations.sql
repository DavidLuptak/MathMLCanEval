insert into configurations (id, content, note, name, visible_to_public, "user") values (1, '<?xml version="1.0" encoding="UTF-8"?>
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
