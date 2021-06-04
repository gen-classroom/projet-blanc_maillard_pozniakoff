package ch.heigvd.statique.benchmark;

import ch.heigvd.statique.config.PAttributeProvider;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.openjdk.jmh.annotations.*;

import java.io.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class MdToHTMLBenchmark {

    private Parser parser;
    private HtmlRenderer renderer;
    private File input;
    private String example;
    private File output;
    private InputStreamReader file;
    private BufferedWriter out;

    @Setup
    public void setup() throws IOException {

        //Example found on markdowntohtml.com
        example = "# Sample Markdown\n" +
                "\n" +
                "This is some basic, sample markdown.\n" +
                "\n" +
                "## Second Heading\n" +
                "\n" +
                " * Unordered lists, and:\n" +
                "  1. One\n" +
                "  1. Two\n" +
                "  1. Three\n" +
                " * More\n" +
                "\n" +
                "> Blockquote\n" +
                "\n" +
                "And **bold**, *italics*, and even *italics and later **bold***. Even ~~strikethrough~~. [A link](https://markdowntohtml.com) to somewhere.\n" +
                "\n" +
                "And code highlighting:\n" +
                "\n" +
                "```js\n" +
                "var foo = 'bar';\n" +
                "\n" +
                "function baz(s) {\n" +
                "   return foo + ':' + s;\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "Or inline code like `var foo = 'bar';`.\n" +
                "\n" +
                "Or an image of bears\n" +
                "\n" +
                "![bears](http://placebear.com/200/200)\n" +
                "\n" +
                "The end ...\n";

        input = new File("input.md");

        BufferedWriter exOut = new BufferedWriter(new FileWriter(input));
        exOut.write(example);
        exOut.flush();

        file = new InputStreamReader(new FileInputStream(input));

        output = new File("output.html");
        out = new BufferedWriter(new FileWriter(output));

        parser = Parser.builder().build();
        renderer = HtmlRenderer.builder().
                attributeProviderFactory(attributeProviderContext -> new PAttributeProvider()).
                build();
    }

    @TearDown
    public void tearDown() throws IOException {
        file.close();
        out.close();
        if(input.exists())
            input.delete();
        if(output.exists())
            output.delete();
    }

    @Benchmark
    public void benchmark() throws IOException {
        Node document = parser.parseReader(file);
        out.write(renderer.render(document));
        out.flush();
    }

}
