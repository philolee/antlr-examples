package com.philolee.antlr;

import com.philolee.antlr.calculator.CalculatorBaseListener;
import com.philolee.antlr.calculator.CalculatorParser;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

import java.util.HashMap;
import java.util.Map;

public class CalculatorCompilerAnalyzer extends CalculatorBaseListener {

  private static final Map<String, Double> params = new HashMap<>();
  private final ParseTreeProperty<Object> valProperty = new ParseTreeProperty<>();

  @Override public void exitAssign(@NotNull CalculatorParser.AssignContext ctx) {
    Double value = (Double) valProperty.get(ctx.expr());
    params.put(ctx.ID().getText(), value);
  }

  @Override public void exitId(@NotNull CalculatorParser.IdContext ctx) {
    Double paramValue = params.get(ctx.ID().getText());
    if (paramValue != null) {
      valProperty.put(ctx, paramValue);
    } else {
      System.out.println(ctx.ID() + " not found.");
    }
  }

  @Override public void exitInt(@NotNull CalculatorParser.IntContext ctx) {
    valProperty.put(ctx, Double.valueOf(ctx.INT().getText()));
  }

  @Override public void exitProg(@NotNull CalculatorParser.ProgContext ctx) {
  }

  @Override public void exitPrintExpr(@NotNull CalculatorParser.PrintExprContext ctx) {
    Double value = (Double) valProperty.get(ctx.expr());
    if (value != null)
      System.out.println(value);
  }

  @Override public void exitAddSub(@NotNull CalculatorParser.AddSubContext ctx) {
    Double value1 = (Double) valProperty.get(ctx.expr(0));
    Double value2 = (Double) valProperty.get(ctx.expr(1));
    if (ctx.ADD() != null) {
      valProperty.put(ctx, value1 + value2);
    } else if (ctx.SUB() != null) {
      valProperty.put(ctx, value1 - value2);
    }
  }

  @Override public void exitMulDiv(@NotNull CalculatorParser.MulDivContext ctx) {
    Double value1 = (Double) valProperty.get(ctx.expr(0));
    Double value2 = (Double) valProperty.get(ctx.expr(1));
    if (ctx.MUL() != null) {
      valProperty.put(ctx, value1 * value2);
    } else if (ctx.DIV() != null) {
      valProperty.put(ctx, value1 / value2);
    }
  }

  @Override public void exitParens(@NotNull CalculatorParser.ParensContext ctx) {
    valProperty.put(ctx, valProperty.get(ctx.expr()));
  }
}
