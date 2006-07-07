package org.eclipse.gmf.codegen.templates.parts;

import java.util.*;
import org.eclipse.emf.codegen.ecore.genmodel.*;
import org.eclipse.gmf.codegen.gmfgen.*;
import org.eclipse.gmf.common.codegen.*;

public class NodeEditPartGenerator {
 
  protected static String nl;
  public static synchronized NodeEditPartGenerator create(String lineSeparator)
  {
    nl = lineSeparator;
    NodeEditPartGenerator result = new NodeEditPartGenerator();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + "/*" + NL + " *";
  protected final String TEXT_3 = NL + " */";
  protected final String TEXT_4 = NL;
  protected final String TEXT_5 = NL + NL + "/**" + NL + " * @generated" + NL + " */" + NL + "public class ";
  protected final String TEXT_6 = " extends ";
  protected final String TEXT_7 = " {";
  protected final String TEXT_8 = NL;
  protected final String TEXT_9 = NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic static final int VISUAL_ID = ";
  protected final String TEXT_10 = ";";
  protected final String TEXT_11 = NL + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected IFigure contentPane;" + NL + "" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected IFigure primaryShape;" + NL + "" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_12 = "(View view) {" + NL + "\t\tsuper(view);" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected void createDefaultEditPolicies() {";
  protected final String TEXT_13 = NL + "\t\tinstallEditPolicy(EditPolicyRoles.CREATION_ROLE, new ";
  protected final String TEXT_14 = "()";
  protected final String TEXT_15 = NL + "\t\t{" + NL + "" + NL + "\t\t\tpublic ";
  protected final String TEXT_16 = " getCommand(";
  protected final String TEXT_17 = " request) {" + NL + "\t\t\t\tif (understandsRequest(request)) {" + NL + "\t\t\t\t\tif (request instanceof ";
  protected final String TEXT_18 = ") {" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_19 = " adapter =" + NL + "\t\t\t\t\t\t\t((";
  protected final String TEXT_20 = ") request).getViewAndElementDescriptor().getCreateElementRequestAdapter();" + NL + "\t\t\t\t\t\t";
  protected final String TEXT_21 = " type =" + NL + "\t\t\t\t\t\t\t(";
  protected final String TEXT_22 = ") adapter.getAdapter(";
  protected final String TEXT_23 = ".class);";
  protected final String TEXT_24 = NL + "\t\t\t\t\t\tif (type == ";
  protected final String TEXT_25 = ".";
  protected final String TEXT_26 = ") {" + NL + "\t\t\t\t\t\t\t";
  protected final String TEXT_27 = " compartmentEditPart =" + NL + "\t\t\t\t\t\t\t\tgetChildBySemanticHint(";
  protected final String TEXT_28 = ".getType(";
  protected final String TEXT_29 = ".VISUAL_ID));" + NL + "\t\t\t\t\t\t\treturn compartmentEditPart == null ? null : compartmentEditPart.getCommand(request);" + NL + "\t\t\t\t\t\t}";
  protected final String TEXT_30 = NL + "\t\t\t\t\t}" + NL + "\t\t\t\t\treturn super.getCommand(request);" + NL + "\t\t\t\t}" + NL + "\t\t\t\treturn null;" + NL + "\t\t\t}" + NL + "\t\t}";
  protected final String TEXT_31 = NL + "\t\t);";
  protected final String TEXT_32 = NL + "\t\tsuper.createDefaultEditPolicies();" + NL + "\t\tinstallEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new ";
  protected final String TEXT_33 = "());" + NL + "\t\tinstallEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new ";
  protected final String TEXT_34 = "());";
  protected final String TEXT_35 = NL + "\t\tinstallEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE, new ";
  protected final String TEXT_36 = "());";
  protected final String TEXT_37 = NL + "\t\tinstallEditPolicy(EditPolicyRoles.CANONICAL_ROLE, new ";
  protected final String TEXT_38 = "());";
  protected final String TEXT_39 = NL + "\t\tinstallEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());";
  protected final String TEXT_40 = NL;
  protected final String TEXT_41 = NL + "\t\tinstallEditPolicy(\"";
  protected final String TEXT_42 = "\", new ";
  protected final String TEXT_43 = "()); //$NON-NLS-1$";
  protected final String TEXT_44 = NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected ";
  protected final String TEXT_45 = " createLayoutEditPolicy() {";
  protected final String TEXT_46 = NL + "\t\t";
  protected final String TEXT_47 = " lep = new ";
  protected final String TEXT_48 = "() {";
  protected final String TEXT_49 = NL + NL + "\t\t\tprotected void decorateChild(EditPart child) {" + NL + "\t\t\t\tif (isExternalLabel(child)) {" + NL + "\t\t\t\t\treturn;" + NL + "\t\t\t\t}" + NL + "\t\t\t\tsuper.decorateChild(child);" + NL + "\t\t\t}";
  protected final String TEXT_50 = NL + NL + "\t\t\tprotected EditPolicy createChildEditPolicy(";
  protected final String TEXT_51 = " child) {" + NL + "\t\t\t\tEditPolicy result = super.createChildEditPolicy(child);" + NL + "\t\t\t\tif (result == null) {" + NL + "\t\t\t\t\treturn new ";
  protected final String TEXT_52 = "();" + NL + "\t\t\t\t}" + NL + "\t\t\t\treturn result;" + NL + "\t\t\t}" + NL + "\t\t};";
  protected final String TEXT_53 = NL + "\t\t";
  protected final String TEXT_54 = " xlep = new ";
  protected final String TEXT_55 = "() {" + NL + "" + NL + "\t\t\tprotected boolean isExternalLabel(EditPart editPart) {" + NL + "\t\t\t\treturn ";
  protected final String TEXT_56 = ".this.isExternalLabel(editPart);" + NL + "\t\t\t}" + NL + "\t\t};" + NL + "\t\txlep.setRealLayoutEditPolicy(lep);" + NL + "\t\treturn xlep;";
  protected final String TEXT_57 = NL + "\t\treturn lep;";
  protected final String TEXT_58 = NL + "\t\t";
  protected final String TEXT_59 = " lep = new ";
  protected final String TEXT_60 = "() {";
  protected final String TEXT_61 = NL + NL + "\t\t\tprotected void decorateChild(EditPart child) {" + NL + "\t\t\t\tif (isExternalLabel(child)) {" + NL + "\t\t\t\t\treturn;" + NL + "\t\t\t\t}" + NL + "\t\t\t\tsuper.decorateChild(child);" + NL + "\t\t\t}";
  protected final String TEXT_62 = NL + NL + "\t\t\tprotected EditPolicy createChildEditPolicy(";
  protected final String TEXT_63 = " child) {" + NL + "\t\t\t\tif (child.getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE) == null) {" + NL + "\t\t\t\t\tif (child instanceof ";
  protected final String TEXT_64 = ") {" + NL + "\t\t\t\t\t\treturn new ";
  protected final String TEXT_65 = "();" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t}" + NL + "\t\t\t\treturn super.createChildEditPolicy(child);" + NL + "\t\t\t}" + NL + "\t\t};";
  protected final String TEXT_66 = NL + "\t\t";
  protected final String TEXT_67 = " xlep = new ";
  protected final String TEXT_68 = "() {" + NL + "" + NL + "\t\t\tprotected boolean isExternalLabel(EditPart editPart) {" + NL + "\t\t\t\treturn ";
  protected final String TEXT_69 = ".this.isExternalLabel(editPart);" + NL + "\t\t\t}" + NL + "\t\t};" + NL + "\t\txlep.setRealLayoutEditPolicy(lep);" + NL + "\t\treturn xlep;";
  protected final String TEXT_70 = NL + "\t\treturn lep;";
  protected final String TEXT_71 = NL + "\t\t";
  protected final String TEXT_72 = " lep = new ";
  protected final String TEXT_73 = "() {";
  protected final String TEXT_74 = NL + NL + "\t\t\tprotected void decorateChild(EditPart child) {" + NL + "\t\t\t\tif (isExternalLabel(child)) {" + NL + "\t\t\t\t\treturn;" + NL + "\t\t\t\t}" + NL + "\t\t\t\tsuper.decorateChild(child);" + NL + "\t\t\t}";
  protected final String TEXT_75 = NL + NL + "\t\t\tprotected ";
  protected final String TEXT_76 = " createAddCommand(";
  protected final String TEXT_77 = " child, ";
  protected final String TEXT_78 = " after) {" + NL + "\t\t\t\treturn null;" + NL + "\t\t\t}" + NL + "" + NL + "\t\t\tprotected ";
  protected final String TEXT_79 = " createMoveChildCommand(";
  protected final String TEXT_80 = " child, ";
  protected final String TEXT_81 = " after) {" + NL + "\t\t\t\treturn null;" + NL + "\t\t\t}" + NL + "" + NL + "\t\t\tprotected ";
  protected final String TEXT_82 = " getCreateCommand(";
  protected final String TEXT_83 = " request) {" + NL + "\t\t\t\treturn null;" + NL + "\t\t\t}" + NL + "\t\t};";
  protected final String TEXT_84 = NL + "\t\t";
  protected final String TEXT_85 = " xlep = new ";
  protected final String TEXT_86 = "() {" + NL + "" + NL + "\t\t\tprotected boolean isExternalLabel(EditPart editPart) {" + NL + "\t\t\t\treturn ";
  protected final String TEXT_87 = ".this.isExternalLabel(editPart);" + NL + "\t\t\t}" + NL + "\t\t};" + NL + "\t\txlep.setRealLayoutEditPolicy(lep);" + NL + "\t\treturn xlep;";
  protected final String TEXT_88 = NL + "\t\treturn lep;";
  protected final String TEXT_89 = NL + "\t\t";
  protected final String TEXT_90 = " lep = new ";
  protected final String TEXT_91 = "() {";
  protected final String TEXT_92 = NL + NL + "\t\t\tprotected void decorateChild(EditPart child) {" + NL + "\t\t\t\tif (isExternalLabel(child)) {" + NL + "\t\t\t\t\treturn;" + NL + "\t\t\t\t}" + NL + "\t\t\t\tsuper.decorateChild(child);" + NL + "\t\t\t}";
  protected final String TEXT_93 = NL + NL + "\t\t\tprotected EditPolicy createChildEditPolicy(";
  protected final String TEXT_94 = " child) {" + NL + "\t\t\t\tEditPolicy result = child.getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);" + NL + "\t\t\t\tif( result == null ){" + NL + "\t\t\t\t\tresult = new ";
  protected final String TEXT_95 = "();" + NL + "\t\t\t\t}" + NL + "\t\t\t\treturn result;" + NL + "\t\t\t}" + NL + "" + NL + "\t\t\tprotected ";
  protected final String TEXT_96 = " getMoveChildrenCommand(";
  protected final String TEXT_97 = " request) {" + NL + "\t\t\t\treturn null;" + NL + "\t\t\t}" + NL + "" + NL + "\t\t\tprotected ";
  protected final String TEXT_98 = " getCreateCommand(";
  protected final String TEXT_99 = " request) {" + NL + "\t\t\t\treturn null;" + NL + "\t\t\t}" + NL + "\t\t};";
  protected final String TEXT_100 = NL + "\t\t";
  protected final String TEXT_101 = " xlep = new ";
  protected final String TEXT_102 = "() {" + NL + "" + NL + "\t\t\tprotected boolean isExternalLabel(EditPart editPart) {" + NL + "\t\t\t\treturn ";
  protected final String TEXT_103 = ".this.isExternalLabel(editPart);" + NL + "\t\t\t}" + NL + "\t\t};" + NL + "\t\txlep.setRealLayoutEditPolicy(lep);" + NL + "\t\treturn xlep;";
  protected final String TEXT_104 = NL + "\t\treturn lep;";
  protected final String TEXT_105 = NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected IFigure createNodeShape() {";
  protected final String TEXT_106 = NL + "\t\treturn primaryShape = new ";
  protected final String TEXT_107 = "()";
  protected final String TEXT_108 = " {" + NL + "\t\t\tprotected boolean useLocalCoordinates() {" + NL + "\t\t\t\treturn true;" + NL + "\t\t\t}" + NL + "\t\t}";
  protected final String TEXT_109 = ";";
  protected final String TEXT_110 = NL + "\t\treturn ";
  protected final String TEXT_111 = ";";
  protected final String TEXT_112 = NL + "\t\t";
  protected final String TEXT_113 = " figure = new ";
  protected final String TEXT_114 = "();";
  protected final String TEXT_115 = NL + " \t\tfigure.setUseLocalCoordinates(true);";
  protected final String TEXT_116 = NL + " \t\treturn primaryShape = figure;";
  protected final String TEXT_117 = NL + "\t}";
  protected final String TEXT_118 = NL + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_119 = " getPrimaryShape() {" + NL + "\t\treturn (";
  protected final String TEXT_120 = ") primaryShape;" + NL + "\t}";
  protected final String TEXT_121 = NL;
  protected final String TEXT_122 = NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected boolean addFixedChild(EditPart childEditPart) {";
  protected final String TEXT_123 = NL + "\t\tif (childEditPart instanceof ";
  protected final String TEXT_124 = ") {" + NL + "\t\t\t((";
  protected final String TEXT_125 = ") childEditPart).";
  protected final String TEXT_126 = "(getPrimaryShape().";
  protected final String TEXT_127 = "());" + NL + "\t\t\treturn true;" + NL + "\t\t}";
  protected final String TEXT_128 = NL + "\t\tif (childEditPart instanceof ";
  protected final String TEXT_129 = ") {" + NL + "\t\t\t";
  protected final String TEXT_130 = " pane = getPrimaryShape().";
  protected final String TEXT_131 = "();" + NL + "\t\t\tsetupContentPane(pane); // FIXME each comparment should handle his content pane in his own way " + NL + "\t\t\tpane.add(((";
  protected final String TEXT_132 = ")childEditPart).getFigure());" + NL + "\t\t\treturn true;" + NL + "\t\t}\t";
  protected final String TEXT_133 = NL + "\t\treturn false;" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected boolean removeFixedChild(EditPart childEditPart) {";
  protected final String TEXT_134 = "\t\t" + NL + "\t\tif (childEditPart instanceof ";
  protected final String TEXT_135 = ") {" + NL + "\t\t\t";
  protected final String TEXT_136 = " pane = getPrimaryShape().";
  protected final String TEXT_137 = "();" + NL + "\t\t\tpane.remove(((";
  protected final String TEXT_138 = ")childEditPart).getFigure());" + NL + "\t\t\treturn true;" + NL + "\t\t}\t";
  protected final String TEXT_139 = NL + "\t\treturn false;" + NL + "\t}";
  protected final String TEXT_140 = NL + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected ";
  protected final String TEXT_141 = " createNodePlate() {";
  protected final String TEXT_142 = NL + "\t\treturn new ";
  protected final String TEXT_143 = "(getMapMode().DPtoLP(";
  protected final String TEXT_144 = "), getMapMode().DPtoLP(";
  protected final String TEXT_145 = "));" + NL + "\t}";
  protected final String TEXT_146 = NL + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic EditPolicy getPrimaryDragEditPolicy() {" + NL + "\t\t";
  protected final String TEXT_147 = " ep = (";
  protected final String TEXT_148 = ") super.getPrimaryDragEditPolicy();" + NL + "\t\t";
  protected final String TEXT_149 = NL + "\t\tep.setResizeDirections(";
  protected final String TEXT_150 = ".NONE);" + NL + "\t\t";
  protected final String TEXT_151 = NL + "\t\tep.setResizeDirections(";
  protected final String TEXT_152 = ".";
  protected final String TEXT_153 = " | ";
  protected final String TEXT_154 = ");" + NL + "\t\t";
  protected final String TEXT_155 = NL + "\t\treturn ep;" + NL + "\t}";
  protected final String TEXT_156 = NL + "\t/**" + NL + "\t * Creates figure for this edit part." + NL + "\t * " + NL + "\t * Body of this method does not depend on settings in generation model" + NL + "\t * so you may safely remove <i>generated</i> tag and modify it." + NL + "\t * " + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected ";
  protected final String TEXT_157 = " createNodeFigure() {" + NL + "\t\t";
  protected final String TEXT_158 = " figure = createNodePlate();" + NL + "\t\tfigure.setLayoutManager(new StackLayout());" + NL + "\t\tIFigure shape = createNodeShape();" + NL + "\t\tfigure.add(shape);" + NL + "\t\tcontentPane = setupContentPane(shape);" + NL + "\t\treturn figure;" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * Default implementation treats passed figure as content pane." + NL + "\t * Respects layout one may have set for generated figure." + NL + "\t * @param nodeShape instance of generated figure class" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected IFigure setupContentPane(IFigure nodeShape) {" + NL + "\t\tif (nodeShape.getLayoutManager() == null) {";
  protected final String TEXT_159 = NL + "\t\tnodeShape.setLayoutManager(new ";
  protected final String TEXT_160 = "() {" + NL + "" + NL + "\t\t\tpublic Object getConstraint(IFigure figure) {" + NL + "\t\t\t\tObject result = constraints.get(figure);" + NL + "\t\t\t\tif (result == null) {" + NL + "\t\t\t\t\tresult = new ";
  protected final String TEXT_161 = "(0, 0, -1, -1);" + NL + "\t\t\t\t}" + NL + "\t\t\t\treturn result;" + NL + "\t\t\t}" + NL + "\t\t});";
  protected final String TEXT_162 = NL + "\t\t\t";
  protected final String TEXT_163 = " layout = new ";
  protected final String TEXT_164 = "();" + NL + "\t\t\tlayout.setSpacing(getMapMode().DPtoLP(5));" + NL + "\t\t\tnodeShape.setLayoutManager(layout);";
  protected final String TEXT_165 = NL + "\t\t}" + NL + "\t\treturn nodeShape; // use nodeShape itself as contentPane" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic IFigure getContentPane() {" + NL + "\t\tif (contentPane != null) {" + NL + "\t\t\treturn contentPane;" + NL + "\t\t}" + NL + "\t\treturn super.getContentPane();" + NL + "\t}";
  protected final String TEXT_166 = NL + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_167 = " getPrimaryChildEditPart() {" + NL + "\t\treturn getChildBySemanticHint(";
  protected final String TEXT_168 = ".getType(";
  protected final String TEXT_169 = ".VISUAL_ID));" + NL + "\t}";
  protected final String TEXT_170 = NL + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected boolean isExternalLabel(";
  protected final String TEXT_171 = " childEditPart) {";
  protected final String TEXT_172 = NL + "\t\tif (childEditPart instanceof ";
  protected final String TEXT_173 = ") {" + NL + "\t\t\treturn true;" + NL + "\t\t}";
  protected final String TEXT_174 = NL + "\t\treturn false;" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected IFigure getExternalLabelsContainer() {" + NL + "\t\t";
  protected final String TEXT_175 = " root = (";
  protected final String TEXT_176 = ") getRoot();" + NL + "\t\treturn root.getLayer(";
  protected final String TEXT_177 = ".EXTERNAL_NODE_LABELS_LAYER);" + NL + "\t}" + NL;
  protected final String TEXT_178 = NL + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected void addChildVisual(";
  protected final String TEXT_179 = " childEditPart, int index) {";
  protected final String TEXT_180 = NL + "\t\tif (isExternalLabel(childEditPart)) {" + NL + "\t\t\tIFigure labelFigure = ((";
  protected final String TEXT_181 = ") childEditPart).getFigure();" + NL + "\t\t\tgetExternalLabelsContainer().add(labelFigure);" + NL + "\t\t\treturn;" + NL + "\t\t} ";
  protected final String TEXT_182 = "\t\t" + NL + "\t\tif (addFixedChild(childEditPart)) {" + NL + "\t\t\treturn;" + NL + "\t\t}";
  protected final String TEXT_183 = NL + "\t\tsuper.addChildVisual(childEditPart, -1);" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected void removeChildVisual(";
  protected final String TEXT_184 = " childEditPart) {";
  protected final String TEXT_185 = NL + "\t\tif (isExternalLabel(childEditPart)) {" + NL + "\t\t\tIFigure labelFigure = ((";
  protected final String TEXT_186 = ") childEditPart).getFigure();" + NL + "\t\t\tgetExternalLabelsContainer().remove(labelFigure);" + NL + "\t\t\treturn;" + NL + "\t\t} ";
  protected final String TEXT_187 = NL + "\t\tif (removeFixedChild(childEditPart)){" + NL + "\t\t\treturn;" + NL + "\t\t}";
  protected final String TEXT_188 = NL + "\t\tsuper.removeChildVisual(childEditPart);" + NL + "\t}";
  protected final String TEXT_189 = "\t\t" + NL + "" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic void removeNotify() {" + NL + "\t\tfor (";
  protected final String TEXT_190 = " it = getChildren().iterator(); it.hasNext();) {" + NL + "\t\t\t";
  protected final String TEXT_191 = " childEditPart = (";
  protected final String TEXT_192 = ") it.next();" + NL + "\t\t\tif (isExternalLabel(childEditPart)) {" + NL + "\t\t\t\tIFigure labelFigure = ((";
  protected final String TEXT_193 = ") childEditPart).getFigure();" + NL + "\t\t\t\tgetExternalLabelsContainer().remove(labelFigure);" + NL + "\t\t\t}" + NL + "\t\t}" + NL + "\t\tsuper.removeNotify();" + NL + "\t}";
  protected final String TEXT_194 = "\t" + NL;
  protected final String TEXT_195 = NL;
  protected final String TEXT_196 = NL + "}";
  protected final String TEXT_197 = NL;

	protected final String getFeatureValueGetter(String containerName, GenFeature feature, boolean isContainerEObject, ImportAssistant importManager) {
		StringBuffer result = new StringBuffer();
		if (feature.getGenClass().isExternalInterface()) {
// Using EMF reflective method to access feature value
			result.append("((");
			if (feature.isListType()) {
				result.append(importManager.getImportedName("java.util.Collection"));
			} else {
				result.append(importManager.getImportedName(feature.getTypeGenClass().getQualifiedInterfaceName()));
			}
			result.append(")");
			if (!isContainerEObject) {
// Casting container to EObject - ExternalIntarfce could be not an instance of EObject
				result.append("((");
				result.append(importManager.getImportedName("org.eclipse.emf.ecore.EObject"));
				result.append(")");
			}
			result.append(containerName);
			if (!isContainerEObject) {
				result.append(")");
			}
			result.append(".eGet(");
			result.append(importManager.getImportedName(feature.getGenPackage().getQualifiedPackageInterfaceName()));
			result.append(".eINSTANCE.get");
			result.append(feature.getFeatureAccessorName());
			result.append("()))");
		} else {
			if (isContainerEObject) {
// Casting container to the typed interface
				result.append("((");
				result.append(importManager.getImportedName(feature.getGenClass().getQualifiedInterfaceName()));
				result.append(")");
			}
			result.append(containerName);
			if (isContainerEObject) {
				result.append(")");
			}
			result.append(".");
			result.append(feature.getGetAccessor());
			result.append("()");
		}
		return result.toString();
	}
	
	protected final String getFeatureValueSetterPrefix(String containerName, GenFeature feature, boolean isContainerEObject, ImportAssistant importManager) {
		StringBuffer result = new StringBuffer();
		if (feature.getGenClass().isExternalInterface()) {
// Using EMF reflective method to access feature value
			if (!isContainerEObject) {
// Casting container to EObject - ExternalIntarfce could be not an instance of EObject
				result.append("((");
				result.append(importManager.getImportedName("org.eclipse.emf.ecore.EObject"));
				result.append(")");
			}
			result.append(containerName);
			if (!isContainerEObject) {
				result.append(")");
			}
			result.append(".eSet(");
			result.append(importManager.getImportedName(feature.getGenPackage().getQualifiedPackageInterfaceName()));
			result.append(".eINSTANCE.get");
			result.append(feature.getFeatureAccessorName());
			result.append("(), ");
		} else {
			if (isContainerEObject) {
// Casting container to the typed interface
				result.append("((");
				result.append(importManager.getImportedName(feature.getGenClass().getQualifiedInterfaceName()));
				result.append(")");
			}
			result.append(containerName);
			if (isContainerEObject) {
				result.append(")");
			}
			if (feature.isListType()) {
				result.append(".");
				result.append(feature.getGetAccessor());
				result.append("().add(");
			} else {
				result.append(".set");
				result.append(feature.getAccessorName());
				result.append("(");
			}
		}
		return result.toString();
	}
 
	protected final String getMetaClassAccessor(GenClass genClass, ImportAssistant importManager) {
		StringBuffer buf = new StringBuffer();
		buf.append(importManager.getImportedName(genClass.getGenPackage().getQualifiedPackageInterfaceName()))
			.append(".eINSTANCE.get") //$NON-NLS-1$
			.append(genClass.getName())
			.append("()"); //$NON-NLS-1$
		return buf.toString();
	} 
 
	public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
final GenNode genNode = (GenNode) ((Object[]) argument)[0];
final ImportAssistant importManager = (ImportAssistant) ((Object[]) argument)[1];
GenDiagram genDiagram = genNode.getDiagram();
boolean isXYLayout = ViewmapLayoutType.XY_LAYOUT_LITERAL.equals(genNode.getLayoutType());

class NodeEditPartHelper {
	private final List myInnerLabels = new LinkedList();
	private final List myExternalLabels = new LinkedList();
	private final List myPinnedCompartments = new LinkedList();
	private final List myFloatingCompartments = new LinkedList();
	private GenNodeLabel myPrimaryLabel;
	private boolean myHasChildrenInListCompartments = false;
	
	public NodeEditPartHelper(GenNode genNode){
		myPrimaryLabel = null;

		for (Iterator labels = genNode.getLabels().iterator(); labels.hasNext();) {
			GenNodeLabel next = (GenNodeLabel) labels.next();
			if (myPrimaryLabel == null){
				myPrimaryLabel = next;
			}
			if (next instanceof GenExternalNodeLabel) {
				myExternalLabels.add(next);
			} else if (next.getViewmap() instanceof ParentAssignedViewmap) {
				myInnerLabels.add(next);
			}
		}
		
		for (Iterator compartments = genNode.getCompartments().iterator(); compartments.hasNext();){
			GenCompartment next = (GenCompartment) compartments.next();
			if (next.getViewmap() instanceof ParentAssignedViewmap){
				myPinnedCompartments.add(next);
			} else {
				myFloatingCompartments.add(next);
			}	
			
			myHasChildrenInListCompartments |= next.isListLayout() && !next.getChildNodes().isEmpty();
		}
	}
	
	public boolean hasChildrenInListCompartments(){
		return myHasChildrenInListCompartments;
	}
	
	public boolean hasInnerFixedLabels(){
		return !myInnerLabels.isEmpty();
	}
	
	public boolean hasPinnedCompartments(){
		return !myPinnedCompartments.isEmpty();
	}
	
	public boolean hasFixedChildren(){
		return hasInnerFixedLabels() || hasPinnedCompartments();
	}
	
	public boolean hasExternalLabels(){
		return !myExternalLabels.isEmpty();
	}
	
	public GenNodeLabel getPrimaryLabel(){
		return myPrimaryLabel;
	}
	
	public Iterator getInnerFixedLabels(){
		return myInnerLabels.iterator();
	}
	
	public Iterator getExternalLabels(){
		return myExternalLabels.iterator();
	}
	
	public Iterator getPinnedCompartments(){
		return myPinnedCompartments.iterator();
	}	
}
final NodeEditPartHelper myHelper = new NodeEditPartHelper(genNode);

    stringBuffer.append(TEXT_1);
    
String copyrightText = genDiagram.getEditorGen().getCopyrightText();
if (copyrightText != null && copyrightText.trim().length() > 0) {

    stringBuffer.append(TEXT_2);
    stringBuffer.append(copyrightText.replaceAll("\n", "\n *"));
    stringBuffer.append(TEXT_3);
    }
    importManager.emitPackageStatement(stringBuffer);
    stringBuffer.append(TEXT_4);
    
if (genNode.getViewmap() instanceof InnerClassViewmap) {
importManager.registerInnerClass(((InnerClassViewmap) genNode.getViewmap()).getClassName());
}
importManager.addImport("org.eclipse.draw2d.IFigure");
importManager.addImport("org.eclipse.draw2d.StackLayout");
importManager.addImport("org.eclipse.gef.EditPolicy");
importManager.addImport("org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles");
importManager.addImport("org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure");
importManager.addImport("org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure");
importManager.addImport("org.eclipse.gmf.runtime.notation.View");
importManager.markImportLocation(stringBuffer);

    stringBuffer.append(TEXT_5);
    stringBuffer.append(genNode.getEditPartClassName());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart"));
    stringBuffer.append(TEXT_7);
    {	GenCommonBase genCommonBase = genNode;
    stringBuffer.append(TEXT_8);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(genCommonBase.getVisualID());
    stringBuffer.append(TEXT_10);
    }
    stringBuffer.append(TEXT_11);
    stringBuffer.append(genNode.getEditPartClassName());
    stringBuffer.append(TEXT_12);
    
if (!genNode.getChildNodes().isEmpty() || myHelper.hasChildrenInListCompartments()) {

    stringBuffer.append(TEXT_13);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy"));
    stringBuffer.append(TEXT_14);
    	if (myHelper.hasChildrenInListCompartments()) {
    stringBuffer.append(TEXT_15);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.commands.Command"));
    stringBuffer.append(TEXT_16);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.Request"));
    stringBuffer.append(TEXT_17);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest"));
    stringBuffer.append(TEXT_18);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter"));
    stringBuffer.append(TEXT_19);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest"));
    stringBuffer.append(TEXT_20);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.emf.type.core.IElementType"));
    stringBuffer.append(TEXT_21);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.emf.type.core.IElementType"));
    stringBuffer.append(TEXT_22);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.emf.type.core.IElementType"));
    stringBuffer.append(TEXT_23);
    
		for (Iterator compartments = genNode.getCompartments().iterator(); compartments.hasNext();) {
			GenCompartment compartment = (GenCompartment) compartments.next();
			if (compartment.isListLayout() && !compartment.getChildNodes().isEmpty()) {
				for (Iterator children = compartment.getChildNodes().iterator(); children.hasNext(); ) {
					GenNode child = (GenNode) children.next();

    stringBuffer.append(TEXT_24);
    stringBuffer.append(importManager.getImportedName(genDiagram.getElementTypesQualifiedClassName()));
    stringBuffer.append(TEXT_25);
    stringBuffer.append(child.getUniqueIdentifier());
    stringBuffer.append(TEXT_26);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.EditPart"));
    stringBuffer.append(TEXT_27);
    stringBuffer.append(importManager.getImportedName(genDiagram.getVisualIDRegistryQualifiedClassName()));
    stringBuffer.append(TEXT_28);
    stringBuffer.append(importManager.getImportedName(compartment.getEditPartQualifiedClassName()));
    stringBuffer.append(TEXT_29);
    
				}
			}
		}

    stringBuffer.append(TEXT_30);
    	}
    stringBuffer.append(TEXT_31);
    }
    stringBuffer.append(TEXT_32);
    stringBuffer.append(importManager.getImportedName(genNode.getItemSemanticEditPolicyQualifiedClassName()));
    stringBuffer.append(TEXT_33);
    stringBuffer.append(importManager.getImportedName(genNode.getGraphicalNodeEditPolicyQualifiedClassName()));
    stringBuffer.append(TEXT_34);
    if (!genNode.getChildNodes().isEmpty()) {
    stringBuffer.append(TEXT_35);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy"));
    stringBuffer.append(TEXT_36);
    }

if (genDiagram.isSynchronized()) {
    stringBuffer.append(TEXT_37);
    stringBuffer.append(importManager.getImportedName(genNode.getCanonicalEditPolicyQualifiedClassName()));
    stringBuffer.append(TEXT_38);
    }
    stringBuffer.append(TEXT_39);
    {	GenCommonBase genCommonBase = genNode;
    stringBuffer.append(TEXT_40);
    
for (java.util.Iterator it = genCommonBase.getCustomBehaviour().iterator(); it.hasNext();) {
	CustomBehaviour behaviour = (CustomBehaviour) it.next();

    stringBuffer.append(TEXT_41);
    stringBuffer.append(behaviour.getKey());
    stringBuffer.append(TEXT_42);
    stringBuffer.append(importManager.getImportedName(behaviour.getEditPolicyQualifiedClassName()));
    stringBuffer.append(TEXT_43);
    }
    }
    stringBuffer.append(TEXT_44);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.editpolicies.LayoutEditPolicy"));
    stringBuffer.append(TEXT_45);
    
final String fqnEditPart = importManager.getImportedName("org.eclipse.gef.EditPart");
switch(genNode.getLayoutType().getValue()){
	case ViewmapLayoutType.XY_LAYOUT:

    stringBuffer.append(TEXT_46);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.diagram.ui.editpolicies.XYLayoutEditPolicy"));
    stringBuffer.append(TEXT_47);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.diagram.ui.editpolicies.XYLayoutEditPolicy"));
    stringBuffer.append(TEXT_48);
    		if (myHelper.hasExternalLabels()) {
    stringBuffer.append(TEXT_49);
    		}
    stringBuffer.append(TEXT_50);
    stringBuffer.append(fqnEditPart);
    stringBuffer.append(TEXT_51);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.diagram.ui.editpolicies.ResizableShapeEditPolicy"));
    stringBuffer.append(TEXT_52);
    		if (myHelper.hasExternalLabels()) {
    stringBuffer.append(TEXT_53);
    stringBuffer.append(importManager.getImportedName(genDiagram.getExternalNodeLabelHostLayoutEditPolicyQualifiedClassName()));
    stringBuffer.append(TEXT_54);
    stringBuffer.append(importManager.getImportedName(genDiagram.getExternalNodeLabelHostLayoutEditPolicyQualifiedClassName()));
    stringBuffer.append(TEXT_55);
    stringBuffer.append(genNode.getEditPartClassName());
    stringBuffer.append(TEXT_56);
    		} else {
    stringBuffer.append(TEXT_57);
    
		}
		break;
	case ViewmapLayoutType.TOOLBAR_LAYOUT:

    stringBuffer.append(TEXT_58);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.diagram.ui.editpolicies.ConstrainedToolbarLayoutEditPolicy"));
    stringBuffer.append(TEXT_59);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.diagram.ui.editpolicies.ConstrainedToolbarLayoutEditPolicy"));
    stringBuffer.append(TEXT_60);
    		if (myHelper.hasExternalLabels()) {
    stringBuffer.append(TEXT_61);
    		}
    stringBuffer.append(TEXT_62);
    stringBuffer.append(fqnEditPart);
    stringBuffer.append(TEXT_63);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart"));
    stringBuffer.append(TEXT_64);
    stringBuffer.append(importManager.getImportedName(genDiagram.getTextSelectionEditPolicyQualifiedClassName()));
    stringBuffer.append(TEXT_65);
    		if (myHelper.hasExternalLabels()) {
    stringBuffer.append(TEXT_66);
    stringBuffer.append(importManager.getImportedName(genDiagram.getExternalNodeLabelHostLayoutEditPolicyQualifiedClassName()));
    stringBuffer.append(TEXT_67);
    stringBuffer.append(importManager.getImportedName(genDiagram.getExternalNodeLabelHostLayoutEditPolicyQualifiedClassName()));
    stringBuffer.append(TEXT_68);
    stringBuffer.append(genNode.getEditPartClassName());
    stringBuffer.append(TEXT_69);
    		} else {
    stringBuffer.append(TEXT_70);
    
		}
		break;
	case ViewmapLayoutType.FLOW_LAYOUT:

    stringBuffer.append(TEXT_71);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.editpolicies.FlowLayoutEditPolicy"));
    stringBuffer.append(TEXT_72);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.editpolicies.FlowLayoutEditPolicy"));
    stringBuffer.append(TEXT_73);
    		if (myHelper.hasExternalLabels()) {
    stringBuffer.append(TEXT_74);
    		}
    stringBuffer.append(TEXT_75);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.commands.Command"));
    stringBuffer.append(TEXT_76);
    stringBuffer.append(fqnEditPart);
    stringBuffer.append(TEXT_77);
    stringBuffer.append(fqnEditPart);
    stringBuffer.append(TEXT_78);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.commands.Command"));
    stringBuffer.append(TEXT_79);
    stringBuffer.append(fqnEditPart);
    stringBuffer.append(TEXT_80);
    stringBuffer.append(fqnEditPart);
    stringBuffer.append(TEXT_81);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.commands.Command"));
    stringBuffer.append(TEXT_82);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.requests.CreateRequest"));
    stringBuffer.append(TEXT_83);
    		if (myHelper.hasExternalLabels()) {
    stringBuffer.append(TEXT_84);
    stringBuffer.append(importManager.getImportedName(genDiagram.getExternalNodeLabelHostLayoutEditPolicyQualifiedClassName()));
    stringBuffer.append(TEXT_85);
    stringBuffer.append(importManager.getImportedName(genDiagram.getExternalNodeLabelHostLayoutEditPolicyQualifiedClassName()));
    stringBuffer.append(TEXT_86);
    stringBuffer.append(genNode.getEditPartClassName());
    stringBuffer.append(TEXT_87);
    		} else {
    stringBuffer.append(TEXT_88);
    
		}
		break;
	default:

    stringBuffer.append(TEXT_89);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.editpolicies.LayoutEditPolicy"));
    stringBuffer.append(TEXT_90);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.editpolicies.LayoutEditPolicy"));
    stringBuffer.append(TEXT_91);
    		if (myHelper.hasExternalLabels()) {
    stringBuffer.append(TEXT_92);
    		}
    stringBuffer.append(TEXT_93);
    stringBuffer.append(fqnEditPart);
    stringBuffer.append(TEXT_94);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.editpolicies.NonResizableEditPolicy"));
    stringBuffer.append(TEXT_95);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.commands.Command"));
    stringBuffer.append(TEXT_96);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.Request"));
    stringBuffer.append(TEXT_97);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.commands.Command"));
    stringBuffer.append(TEXT_98);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.requests.CreateRequest"));
    stringBuffer.append(TEXT_99);
    		if (myHelper.hasExternalLabels()) {
    stringBuffer.append(TEXT_100);
    stringBuffer.append(importManager.getImportedName(genDiagram.getExternalNodeLabelHostLayoutEditPolicyQualifiedClassName()));
    stringBuffer.append(TEXT_101);
    stringBuffer.append(importManager.getImportedName(genDiagram.getExternalNodeLabelHostLayoutEditPolicyQualifiedClassName()));
    stringBuffer.append(TEXT_102);
    stringBuffer.append(genNode.getEditPartClassName());
    stringBuffer.append(TEXT_103);
    		} else {
    stringBuffer.append(TEXT_104);
    
		}
} //switch

    stringBuffer.append(TEXT_105);
    
String figureQualifiedClassName = null;
Viewmap viewmap = genNode.getViewmap();
if (viewmap instanceof FigureViewmap) {
	figureQualifiedClassName = ((FigureViewmap) viewmap).getFigureQualifiedClassName();
	if (figureQualifiedClassName == null || figureQualifiedClassName.trim().length() == 0) {
		figureQualifiedClassName = "org.eclipse.draw2d.RectangleFigure";
	}
	figureQualifiedClassName = importManager.getImportedName(figureQualifiedClassName);

    stringBuffer.append(TEXT_106);
    stringBuffer.append(figureQualifiedClassName);
    stringBuffer.append(TEXT_107);
    if (isXYLayout) {
    stringBuffer.append(TEXT_108);
    } // if isXYLayout
    stringBuffer.append(TEXT_109);
    } // instanceof FigureViewmap
 else if (viewmap instanceof SnippetViewmap) {
    stringBuffer.append(TEXT_110);
    stringBuffer.append(((SnippetViewmap) viewmap).getBody());
    stringBuffer.append(TEXT_111);
    } // instanceof SnippetViewmap; FIXME : obtain figure class name to generate getter
 else if (viewmap instanceof InnerClassViewmap) {
 	figureQualifiedClassName = ((InnerClassViewmap) viewmap).getClassName();

    stringBuffer.append(TEXT_112);
    stringBuffer.append(figureQualifiedClassName);
    stringBuffer.append(TEXT_113);
    stringBuffer.append(figureQualifiedClassName);
    stringBuffer.append(TEXT_114);
    if (!genNode.getChildNodes().isEmpty() && isXYLayout) { /*otherwise, leave to figure's default value*/
    stringBuffer.append(TEXT_115);
    }
    stringBuffer.append(TEXT_116);
    }
    stringBuffer.append(TEXT_117);
    if (figureQualifiedClassName != null) {
    stringBuffer.append(TEXT_118);
    stringBuffer.append(figureQualifiedClassName);
    stringBuffer.append(TEXT_119);
    stringBuffer.append(figureQualifiedClassName);
    stringBuffer.append(TEXT_120);
    }
    stringBuffer.append(TEXT_121);
    
if (myHelper.hasFixedChildren()) {

    stringBuffer.append(TEXT_122);
    
for (Iterator it = myHelper.getInnerFixedLabels(); it.hasNext(); ) {
	GenNodeLabel genLabel = (GenNodeLabel) it.next();
	final String labelEditPart = importManager.getImportedName(genLabel.getEditPartQualifiedClassName());
	final ParentAssignedViewmap childViewmap = (ParentAssignedViewmap) genLabel.getViewmap();
	final String childSetterName = childViewmap.getSetterName() == null ? "setLabel" : childViewmap.getSetterName();

    stringBuffer.append(TEXT_123);
    stringBuffer.append(labelEditPart);
    stringBuffer.append(TEXT_124);
    stringBuffer.append(labelEditPart);
    stringBuffer.append(TEXT_125);
    stringBuffer.append(childSetterName);
    stringBuffer.append(TEXT_126);
    stringBuffer.append(childViewmap.getGetterName());
    stringBuffer.append(TEXT_127);
    
}

for (Iterator it = myHelper.getPinnedCompartments(); it.hasNext(); ) {
	GenCompartment next = (GenCompartment) it.next();
	final ParentAssignedViewmap childViewmap = (ParentAssignedViewmap) next.getViewmap();
	String compartmentEditPartFQN = importManager.getImportedName(next.getEditPartQualifiedClassName());

    stringBuffer.append(TEXT_128);
    stringBuffer.append(compartmentEditPartFQN);
    stringBuffer.append(TEXT_129);
    stringBuffer.append(importManager.getImportedName("org.eclipse.draw2d.IFigure"));
    stringBuffer.append(TEXT_130);
    stringBuffer.append(childViewmap.getGetterName());
    stringBuffer.append(TEXT_131);
    stringBuffer.append(compartmentEditPartFQN);
    stringBuffer.append(TEXT_132);
    	
} // for pinned compartments

    stringBuffer.append(TEXT_133);
    
//XXX: ignore labels assuming that they never may be removed
for (Iterator it = myHelper.getPinnedCompartments(); it.hasNext(); ) {
	GenCompartment next = (GenCompartment) it.next();
	final ParentAssignedViewmap childViewmap = (ParentAssignedViewmap) next.getViewmap();
	String compartmentEditPartFQN = importManager.getImportedName(next.getEditPartQualifiedClassName());

    stringBuffer.append(TEXT_134);
    stringBuffer.append(compartmentEditPartFQN);
    stringBuffer.append(TEXT_135);
    stringBuffer.append(importManager.getImportedName("org.eclipse.draw2d.IFigure"));
    stringBuffer.append(TEXT_136);
    stringBuffer.append(childViewmap.getGetterName());
    stringBuffer.append(TEXT_137);
    stringBuffer.append(compartmentEditPartFQN);
    stringBuffer.append(TEXT_138);
    
} // for pinned compartments

    stringBuffer.append(TEXT_139);
    
} // if myHelper.hasFixedChildren()

    stringBuffer.append(TEXT_140);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure"));
    stringBuffer.append(TEXT_141);
    
int width = 40;
int height = 40;
DefaultSizeAttributes defSizeAttrs = (DefaultSizeAttributes) genNode.getViewmap().find(DefaultSizeAttributes.class);
if (defSizeAttrs != null) {
	width = defSizeAttrs.getWidth();
	height = defSizeAttrs.getHeight();
}

    stringBuffer.append(TEXT_142);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure"));
    stringBuffer.append(TEXT_143);
    stringBuffer.append(width);
    stringBuffer.append(TEXT_144);
    stringBuffer.append(height);
    stringBuffer.append(TEXT_145);
    if (genNode.getViewmap().find(ResizeConstraints.class) != null) {
	final ResizeConstraints rc = (ResizeConstraints) genNode.getViewmap().find(ResizeConstraints.class);
	final String draw2dPC = importManager.getImportedName("org.eclipse.draw2d.PositionConstants");
    stringBuffer.append(TEXT_146);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.editpolicies.ResizableEditPolicy"));
    stringBuffer.append(TEXT_147);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.editpolicies.ResizableEditPolicy"));
    stringBuffer.append(TEXT_148);
    if (rc.getResizeHandleNames().isEmpty()) {
    stringBuffer.append(TEXT_149);
    stringBuffer.append(draw2dPC);
    stringBuffer.append(TEXT_150);
     } else {
    stringBuffer.append(TEXT_151);
    for (Iterator rcNamesIter = rc.getResizeHandleNames().iterator(); rcNamesIter.hasNext();) {
			String nextConstantName = (String) rcNamesIter.next();
    stringBuffer.append(draw2dPC);
    stringBuffer.append(TEXT_152);
    stringBuffer.append(nextConstantName);
    if (rcNamesIter.hasNext()) {
    stringBuffer.append(TEXT_153);
    }}
    stringBuffer.append(TEXT_154);
    }
    stringBuffer.append(TEXT_155);
    }
    stringBuffer.append(TEXT_156);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure"));
    stringBuffer.append(TEXT_157);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure"));
    stringBuffer.append(TEXT_158);
    
if (isXYLayout) {

    stringBuffer.append(TEXT_159);
    stringBuffer.append(importManager.getImportedName("org.eclipse.draw2d.FreeformLayout"));
    stringBuffer.append(TEXT_160);
    stringBuffer.append(importManager.getImportedName("org.eclipse.draw2d.geometry.Rectangle"));
    stringBuffer.append(TEXT_161);
    } else {
	String layoutClassName = importManager.getImportedName("org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout");

    stringBuffer.append(TEXT_162);
    stringBuffer.append(layoutClassName);
    stringBuffer.append(TEXT_163);
    stringBuffer.append(layoutClassName);
    stringBuffer.append(TEXT_164);
    }
    stringBuffer.append(TEXT_165);
    
if (myHelper.getPrimaryLabel() != null) {
	GenNodeLabel primaryLabel = myHelper.getPrimaryLabel();

    stringBuffer.append(TEXT_166);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.EditPart"));
    stringBuffer.append(TEXT_167);
    stringBuffer.append(importManager.getImportedName(genDiagram.getVisualIDRegistryQualifiedClassName()));
    stringBuffer.append(TEXT_168);
    stringBuffer.append(importManager.getImportedName(primaryLabel.getEditPartQualifiedClassName()));
    stringBuffer.append(TEXT_169);
    
}
if (myHelper.hasExternalLabels()) {

    stringBuffer.append(TEXT_170);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.EditPart"));
    stringBuffer.append(TEXT_171);
    
	for (Iterator externalLabels = myHelper.getExternalLabels(); externalLabels.hasNext();) {
		GenNodeLabel next = (GenNodeLabel) externalLabels.next();

    stringBuffer.append(TEXT_172);
    stringBuffer.append(importManager.getImportedName(next.getEditPartQualifiedClassName()));
    stringBuffer.append(TEXT_173);
    	}
    stringBuffer.append(TEXT_174);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.editparts.LayerManager"));
    stringBuffer.append(TEXT_175);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.editparts.LayerManager"));
    stringBuffer.append(TEXT_176);
    stringBuffer.append(importManager.getImportedName(genDiagram.getEditPartFactoryQualifiedClassName()));
    stringBuffer.append(TEXT_177);
    
}
if (myHelper.hasFixedChildren() || myHelper.hasExternalLabels()) {

    stringBuffer.append(TEXT_178);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.EditPart"));
    stringBuffer.append(TEXT_179);
    	if (myHelper.hasExternalLabels()) {
    stringBuffer.append(TEXT_180);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.GraphicalEditPart"));
    stringBuffer.append(TEXT_181);
    
	}
	if (myHelper.hasFixedChildren()) {

    stringBuffer.append(TEXT_182);
    	}
    stringBuffer.append(TEXT_183);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.EditPart"));
    stringBuffer.append(TEXT_184);
    	if (myHelper.hasExternalLabels()) {
    stringBuffer.append(TEXT_185);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.GraphicalEditPart"));
    stringBuffer.append(TEXT_186);
    
	}
	if (myHelper.hasFixedChildren()){

    stringBuffer.append(TEXT_187);
    	}
    stringBuffer.append(TEXT_188);
    	if (myHelper.hasExternalLabels()) {
    stringBuffer.append(TEXT_189);
    stringBuffer.append(importManager.getImportedName("java.util.Iterator"));
    stringBuffer.append(TEXT_190);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.EditPart"));
    stringBuffer.append(TEXT_191);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.EditPart"));
    stringBuffer.append(TEXT_192);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gef.GraphicalEditPart"));
    stringBuffer.append(TEXT_193);
    
	}
} // if hasFixedChildren || hasExternalLabels

    stringBuffer.append(TEXT_194);
    if (genNode.getViewmap() instanceof InnerClassViewmap) {
    stringBuffer.append(TEXT_195);
    stringBuffer.append(((InnerClassViewmap) genNode.getViewmap()).getClassBody());
    }
    stringBuffer.append(TEXT_196);
    importManager.emitSortedImports();
    stringBuffer.append(TEXT_197);
    return stringBuffer.toString();
  }
}