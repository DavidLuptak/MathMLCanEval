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
','should work since 1.2', 'defautl config 1.2', false, 1),
(3, '<?xml version="1.0" encoding="UTF-8"?>
<config>

    <module name="ElementMinimizer">
        <!-- remove these elements only (keep children) -->
        <property name="remove">mspace maligngroup malignmark mstyle mpadded menclose maction</property>

        <!-- remove these elements with their children -->
        <property name="remove_all">mphantom merror</property>

        <!-- keep attributes that bear semantics -->
        <property name="keepAttributes">mathvariant=bold mathvariant=italic mathvariant=bold-italic mathvariant=double-struck mathvariant=bold-fraktur mathvariant=script mathvariant=bold-script mathvariant=fraktur mathvariant=sans-serif mathvariant=bold-sans-serif mathvariant=sans-serif-italic mathvariant=sans-serif-bold-italic mathvariant=monospace mathvariant=initial mathvariant=tailed mathvariant=looped mathvariant=stretched encoding</property>
        <property name="keepAttributes.mfrac">linethickness=0</property>
        <property name="keepAttributes.cn">base type</property>
        <property name="keepAttributes.ci">type</property>
        <property name="keepAttributes.set">type=normal type=multiset</property>
        <property name="keepAttributes.tendsto">type=above type=below type=two-sided</property>
        <property name="keepAttributes.interval">closure</property>
        <property name="keepAttributes.declare">nargs occurrence</property>
        <property name="keepAttributes.mfenced">open close</property>
    </module>

    <module name="FunctionNormalizer">
        <!-- function operators -->
        <property name="functionoperators">&#8289;</property>
    </module>

    <module name="MfencedReplacer">
        <!-- add mrow element outside/inside fenced expression when set to 1 (true) -->
        <property name="outermrow">1</property>
        <property name="innermrow">1</property>

        <!-- default values for attributes in mfenced element -->
        <property name="open">(</property>
        <property name="close">)</property>
        <property name="separators">,</property>

        <!-- use always default fences and separators when set to 1 (true) -->
        <property name="forceopen">0</property>
        <property name="forceclose">0</property>
        <property name="forceseparators">0</property>
    </module>

    <module name="MrowNormalizer">
        <!-- whether to wrap fenced formulae resp. their content in mrow -->
        <property name="wrapOutside">1</property>
        <property name="wrapInside">1</property>

        <!-- opening and closing parentheses -->
        <property name="open">( [ { &#10216;</property>
        <property name="close">) ] } &#10217;</property>

        <!--
        initialize childCount according to http://www.w3.org/TR/MathML3/chapter3.html#id.3.1.3.2
        value 1 indicates an inferred mrow as described in the document above
        -->
        <property name="childCount.msqrt">1</property>
        <property name="childCount.mfrac">2</property>
        <property name="childCount.mroot">2</property>
        <property name="childCount.mstyle">1</property>
        <property name="childCount.merror">1</property>
        <property name="childCount.mpadded">1</property>
        <property name="childCount.mphantom">1</property>
        <property name="childCount.mfenced">0</property>
        <property name="childCount.menclose">1</property>
        <property name="childCount.msub">2</property>
        <property name="childCount.msup">2</property>
        <property name="childCount.msubsup">3</property>
        <property name="childCount.munder">2</property>
        <property name="childCount.munderover">3</property>
        <property name="childCount.mtd">1</property>
        <property name="childCount.mscarry">1</property>
        <property name="childCount.math">1</property>
        <property name="childCount.mrow">1</property>
    </module>

    <module name="OperatorNormalizer">
        <!-- remove every empty operators -->
        <property name="removeempty">true</property>

        <!--
        operators which will be removed
        InvisibleTimes, DotOperator, *, InvisibleSeparator, InvisiblePlus
        -->
        <property name="removeoperators">&#8290; &#8901; * &#8291; &#8292;</property>

        <!--
        operators to replece in format "unwanted operator":"replacement"
        +- and -+ to plusminus, hyphen to -
        -->
        <property name="replaceoperators">+-:&#177; -+:&#177; &#173;:- &lt;=:&#8804; =&lt;:&#8804; &#8806;:&#8804; &gt;=:&#8805; =&gt;:&#8805; &#8807;:&#8805;</property>

        <!--
        operator to replace ":" (cannot be set in replaceoperators due to parsing)
        -->
        <property name="colonreplacement">:</property>

        <!--
        normalize all text nodes to the specified Unicode normalization form (NFC, NFD, NFKC or NFKD)
        see http://unicode.org/reports/tr15/ and http://www.unicode.org/charts/normalization/
        leave blank to swith the Unicode normalization off
        -->
        <property name="normalizationform">NFKD</property>

        <!--
        all operators that should not be in mi element (but mo)
        operators from previous properties are added automatically
        -->
        <property name="operators">+ - &lt; &gt; ( ) [ ] { } | ^ ~ &apos; &#8723; &#215; &#183; &#247; &#8260;  &#8730; &#8721; &#8747; &#8750; &#8756; &#8757; ! &#172; &#8733; = &#8800; &#8776; =: := &#8660; :&#8660; &#8796; &#8797; &#8784; &#8773; &#8801; &#8596; &#8810; &#8811; &#8826; &#8827; &#9669; &#9659; &#8658; &#8594; &#8838; &#8834; &#8839; &#8835; &#8614; &#8871; &#8866; &#8917; # &#8768; &#8623; &#8853; &#8891; &#8226; &#8704; &#8706; &#8707; &#8707;! &#8712; &#8713; &#8716; &#8715; o &#8728; &#8224; &#8869; &#8746; &#8745; &#8744; &#8743; &#8855; &#8905; &#8906; &#8904; &#8709;</property>
        <property name="identifiers">exp sin cos tan tg cot cotan cotg ctg ctn sec csc cosec arcsin arccos arctan arccot arcsec arccsc sinh cosh tanh coth cesh csch arcsinh arcosh artanh arcoth arsech arcsch log lg ln</property>
    </module>

    <module name="ScriptNormalizer">
        <!-- changes msub scripts with msup script inside to msup with msub inside -->
        <property name="swapscripts">true</property>

        <!--
        changes msubsup with first child in this list to msup with msub inside
        leave blank to switch the msubsup replacing off
        -->
        <property name="splitscriptselements">mi</property>

        <!--
        converts munder to msub, mover to msup and munderover to msubsup
        conversion is done before sub/sup transformations so these are transformed too
        -->
        <property name="unifyscripts">true</property>
    </module>

    <module name="UnaryOperatorRemover">
        <!--
        operators which will be removed if unary in Presentation MathML
        +       PLUS SIGN
        -       HYPHEN-MINUS
        &#8292; INVISIBLE PLUS
        Operators
        &#8722; MINUS SIGN
        &#8723; MINUS-OR-PLUS SIGN
        &#8724; DOT PLUS
        &#8760; DOT MINUS
        Operators
        &#8853; CIRCLED PLUS
        &#8854; CIRCLED MINUS
        &#8861; CIRCLED DASH
        &#8862; SQUARED PLUS
        &#8863; SQUARED MINUS
        -->
        <property name="pmathremoveunaryoperators">+ - &#8292; &#8722; &#8723; &#8724; &#8760; &#8853; &#8854; &#8861; &#8862; &#8863;</property>

        <!--
        operators which will be removed if applied as unary in Content MathML
        -->
        <property name="cmathremoveunaryoperators">plus minus</property>
    </module>

</config>', null, 'Working 1.4', true, 2);
