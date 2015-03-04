package com.philolee.antlr;

import com.philolee.antlr.calculator.CalculatorLexer;
import com.philolee.antlr.calculator.CalculatorParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CalculatorCompiler {

  public CalculatorCompiler() {
  }

  public void compile(String line) throws IOException {
    ANTLRInputStream input = new ANTLRInputStream(line);
    CalculatorLexer lexer = new CalculatorLexer(input);
    TokenStream tokens = new CommonTokenStream(lexer);
    CalculatorParser parser = new CalculatorParser(tokens);
    CalculatorParser.ProgContext ret = parser.prog();
    CalculatorCompilerAnalyzer analyzer = new CalculatorCompilerAnalyzer();
    ParseTreeWalker.DEFAULT.walk(analyzer, ret);
  }

  public static void main(String[] args) throws IOException {
    CalculatorCompiler compiler = new CalculatorCompiler();
    System.out.println("'q' to quit");
    while (true) {
      System.out.print('>');
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      String line = reader.readLine();
      if (line.equals("q"))
        break;
      compiler.compile(line);
    }
  }
}
