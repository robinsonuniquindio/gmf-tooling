package org.eclipse.gmf.graphdef.codegen.templates;

import org.eclipse.gmf.gmfgraph.*;
import org.eclipse.gmf.common.codegen.*;
import java.util.*;
import org.eclipse.gmf.graphdef.codegen.Dispatcher;

public class ShapeAttrsGenerator
{
  protected static String nl;
  public static synchronized ShapeAttrsGenerator create(String lineSeparator)
  {
    nl = lineSeparator;
    ShapeAttrsGenerator result = new ShapeAttrsGenerator();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "\t\t";
  protected final String TEXT_2 = ".setFill(";
  protected final String TEXT_3 = ");";
  protected final String TEXT_4 = NL + "\t\t";
  protected final String TEXT_5 = ".setOutline(";
  protected final String TEXT_6 = ");";
  protected final String TEXT_7 = NL + "\t\t";
  protected final String TEXT_8 = ".setLineWidth(";
  protected final String TEXT_9 = ");";
  protected final String TEXT_10 = NL + "\t\t";
  protected final String TEXT_11 = ".setLineStyle(";
  protected final String TEXT_12 = ".";
  protected final String TEXT_13 = ");";
  protected final String TEXT_14 = NL + "\t\t";
  protected final String TEXT_15 = ".setFillXOR(";
  protected final String TEXT_16 = ");";
  protected final String TEXT_17 = NL + "\t\t";
  protected final String TEXT_18 = ".setOutlineXOR(";
  protected final String TEXT_19 = ");";
  protected final String TEXT_20 = NL + "\t\t";
  protected final String TEXT_21 = ".addPoint(new ";
  protected final String TEXT_22 = "(";
  protected final String TEXT_23 = ", ";
  protected final String TEXT_24 = "));";
  protected final String TEXT_25 = NL + "\t\t";
  protected final String TEXT_26 = ".setCornerDimensions(new ";
  protected final String TEXT_27 = "(getMapMode().DPtoLP(";
  protected final String TEXT_28 = "), getMapMode().DPtoLP(";
  protected final String TEXT_29 = ")));";
  protected final String TEXT_30 = NL + "\t\t";
  protected final String TEXT_31 = ".setForegroundColor(";
  protected final String TEXT_32 = "new org.eclipse.swt.graphics.Color(null, ";
  protected final String TEXT_33 = ", ";
  protected final String TEXT_34 = ", ";
  protected final String TEXT_35 = ")";
  protected final String TEXT_36 = "org.eclipse.draw2d.ColorConstants.";
  protected final String TEXT_37 = ");";
  protected final String TEXT_38 = NL + "\t\t";
  protected final String TEXT_39 = ".setBackgroundColor(";
  protected final String TEXT_40 = "new org.eclipse.swt.graphics.Color(null, ";
  protected final String TEXT_41 = ", ";
  protected final String TEXT_42 = ", ";
  protected final String TEXT_43 = ")";
  protected final String TEXT_44 = "org.eclipse.draw2d.ColorConstants.";
  protected final String TEXT_45 = ");";
  protected final String TEXT_46 = NL + "\t\t";
  protected final String TEXT_47 = ".setPreferredSize(getMapMode().DPtoLP(";
  protected final String TEXT_48 = "), getMapMode().DPtoLP(";
  protected final String TEXT_49 = "));";

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
    
Dispatcher.Args args = (Dispatcher.Args) argument;
final Shape figureInstance = (Shape) args.getFigure();
final String figureVarName = args.getVariableName();
final ImportAssistant importManager = args.getImportManager();

    
// PERHAPS, do this with reflection?

    if (figureInstance.eIsSet(GMFGraphPackage.eINSTANCE.getShape_Fill())) {
    stringBuffer.append(TEXT_1);
    stringBuffer.append(figureVarName);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(figureInstance.isFill());
    stringBuffer.append(TEXT_3);
    } if (figureInstance.eIsSet(GMFGraphPackage.eINSTANCE.getShape_Outline())) {
    stringBuffer.append(TEXT_4);
    stringBuffer.append(figureVarName);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(figureInstance.isOutline());
    stringBuffer.append(TEXT_6);
    } if (figureInstance.eIsSet(GMFGraphPackage.eINSTANCE.getShape_LineWidth())) {
    stringBuffer.append(TEXT_7);
    stringBuffer.append(figureVarName);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(figureInstance.getLineWidth());
    stringBuffer.append(TEXT_9);
    } if (figureInstance.eIsSet(GMFGraphPackage.eINSTANCE.getShape_LineKind())) {
    stringBuffer.append(TEXT_10);
    stringBuffer.append(figureVarName);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(importManager.getImportedName("org.eclipse.draw2d.Graphics"));
    stringBuffer.append(TEXT_12);
    stringBuffer.append(figureInstance.getLineKind().getName());
    stringBuffer.append(TEXT_13);
    } if (figureInstance.eIsSet(GMFGraphPackage.eINSTANCE.getShape_XorFill())) {
    stringBuffer.append(TEXT_14);
    stringBuffer.append(figureVarName);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(figureInstance.isXorFill());
    stringBuffer.append(TEXT_16);
    } if (figureInstance.eIsSet(GMFGraphPackage.eINSTANCE.getShape_XorOutline())) {
    stringBuffer.append(TEXT_17);
    stringBuffer.append(figureVarName);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(figureInstance.isXorOutline());
    stringBuffer.append(TEXT_19);
    } if (figureInstance instanceof Polyline && !((Polyline) figureInstance).getTemplate().isEmpty()) {
	for (Iterator pointIt = ((Polyline) figureInstance).getTemplate().iterator(); pointIt.hasNext(); ) {
		Point p = (Point) pointIt.next();
    stringBuffer.append(TEXT_20);
    stringBuffer.append(figureVarName);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(importManager.getImportedName("org.eclipse.draw2d.geometry.Point"));
    stringBuffer.append(TEXT_22);
    stringBuffer.append(p.getX());
    stringBuffer.append(TEXT_23);
    stringBuffer.append(p.getY());
    stringBuffer.append(TEXT_24);
    }
    } else if (figureInstance instanceof RoundedRectangle) {
		RoundedRectangle rrFigure = (RoundedRectangle) figureInstance;
    stringBuffer.append(TEXT_25);
    stringBuffer.append(figureVarName);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(importManager.getImportedName("org.eclipse.draw2d.geometry.Dimension"));
    stringBuffer.append(TEXT_27);
    stringBuffer.append(rrFigure.getCornerWidth());
    stringBuffer.append(TEXT_28);
    stringBuffer.append(rrFigure.getCornerHeight());
    stringBuffer.append(TEXT_29);
    }
    Color colorVal;
if (figureInstance.eIsSet(GMFGraphPackage.eINSTANCE.getFigure_ForegroundColor())) {
		colorVal = figureInstance.getForegroundColor();
    stringBuffer.append(TEXT_30);
    stringBuffer.append(figureVarName);
    stringBuffer.append(TEXT_31);
    if (colorVal instanceof RGBColor) {
    stringBuffer.append(TEXT_32);
    stringBuffer.append(((RGBColor) colorVal).getRed());
    stringBuffer.append(TEXT_33);
    stringBuffer.append(((RGBColor) colorVal).getGreen());
    stringBuffer.append(TEXT_34);
    stringBuffer.append(((RGBColor) colorVal).getBlue());
    stringBuffer.append(TEXT_35);
    } else if (colorVal instanceof ConstantColor) {
    stringBuffer.append(TEXT_36);
    stringBuffer.append(((ConstantColor) colorVal).getValue().getLiteral());
    }
    stringBuffer.append(TEXT_37);
    } if (figureInstance.eIsSet(GMFGraphPackage.eINSTANCE.getFigure_BackgroundColor())) {
		colorVal = figureInstance.getBackgroundColor();
    stringBuffer.append(TEXT_38);
    stringBuffer.append(figureVarName);
    stringBuffer.append(TEXT_39);
    if (colorVal instanceof RGBColor) {
    stringBuffer.append(TEXT_40);
    stringBuffer.append(((RGBColor) colorVal).getRed());
    stringBuffer.append(TEXT_41);
    stringBuffer.append(((RGBColor) colorVal).getGreen());
    stringBuffer.append(TEXT_42);
    stringBuffer.append(((RGBColor) colorVal).getBlue());
    stringBuffer.append(TEXT_43);
    } else if (colorVal instanceof ConstantColor) {
    stringBuffer.append(TEXT_44);
    stringBuffer.append(((ConstantColor) colorVal).getValue().getLiteral());
    }
    stringBuffer.append(TEXT_45);
    } if (figureInstance.eIsSet(GMFGraphPackage.eINSTANCE.getFigure_PreferredSize())) {
		Dimension d = figureInstance.getPreferredSize();
    stringBuffer.append(TEXT_46);
    stringBuffer.append(figureVarName);
    stringBuffer.append(TEXT_47);
    stringBuffer.append(d.getDx());
    stringBuffer.append(TEXT_48);
    stringBuffer.append(d.getDy());
    stringBuffer.append(TEXT_49);
    }
    return stringBuffer.toString();
  }
}
