package org.Jacpack.TechUp.util.TagFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

public class TagFile
{
    TreeMap contents = new TreeMap();
    TreeMap comments = new TreeMap();
    String filecomment = "";

    public void addTag(String var1, Object var2)
    {
        int var3 = 0;
        TreeMap var6 = this.contents;

        while (true)
        {
            int var4 = var1.indexOf(46, var3);
            String var5;

            if (var4 < 0)
            {
                var5 = var1.substring(var3);

                if (var5.equals(""))
                {
                    throw new IllegalArgumentException("Empty key name");
                }
                else
                {
                    var6.put(var5, var2);
                    return;
                }
            }

            var5 = var1.substring(var3, var4);
            var3 = var4 + 1;

            if (var5.equals(""))
            {
                throw new IllegalArgumentException("Empty key name");
            }

            Object var8 = var6.get(var5);

            if (var8 == null)
            {
                TreeMap var7 = new TreeMap();
                var6.put(var5, var7);
                var6 = var7;
            }
            else
            {
                if (!(var8 instanceof TreeMap))
                {
                    throw new IllegalArgumentException("Key not a dictionary");
                }

                var6 = (TreeMap)var8;
            }
        }
    }

    public Object getTag(String var1)
    {
        int var2 = 0;
        TreeMap var5 = this.contents;

        while (true)
        {
            int var3 = var1.indexOf(46, var2);
            String var4;

            if (var3 < 0)
            {
                var4 = var1.substring(var2);
                return var5.get(var4);
            }

            var4 = var1.substring(var2, var3);
            var2 = var3 + 1;
            Object var6 = var5.get(var4);

            if (!(var6 instanceof TreeMap))
            {
                return null;
            }

            var5 = (TreeMap)var6;
        }
    }

    public Object removeTag(String var1)
    {
        int var2 = 0;
        TreeMap var5 = this.contents;

        while (true)
        {
            int var3 = var1.indexOf(46, var2);
            String var4;

            if (var3 < 0)
            {
                var4 = var1.substring(var2);
                return var5.remove(var4);
            }

            var4 = var1.substring(var2, var3);
            var2 = var3 + 1;
            Object var6 = var5.get(var4);

            if (!(var6 instanceof TreeMap))
            {
                return null;
            }

            var5 = (TreeMap)var6;
        }
    }

    public void commentTag(String var1, String var2)
    {
        this.comments.put(var1, var2);
    }

    public void commentFile(String var1)
    {
        this.filecomment = var1;
    }

    public void addString(String var1, String var2)
    {
        this.addTag(var1, var2);
    }

    public void addInt(String var1, int var2)
    {
        this.addTag(var1, Integer.valueOf(var2));
    }

    public String getString(String var1)
    {
        Object var2 = this.getTag(var1);
        return !(var2 instanceof String) ? null : (String)var2;
    }

    public String getString(String var1, String var2)
    {
        Object var3 = this.getTag(var1);
        return !(var3 instanceof String) ? var2 : (String)var3;
    }

    public int getInt(String var1)
    {
        Object var2 = this.getTag(var1);
        return !(var2 instanceof Integer) ? 0 : ((Integer)var2).intValue();
    }

    public int getInt(String var1, int var2)
    {
        Object var3 = this.getTag(var1);
        return !(var3 instanceof Integer) ? var2 : ((Integer)var3).intValue();
    }

    private void writecomment(PrintStream var1, String var2, String var3)
    {
        if (var3 != null)
        {
            String[] var4 = var3.split("\n");
            int var5 = var4.length;

            for (int var6 = 0; var6 < var5; ++var6)
            {
                String var7 = var4[var6];
                var1.printf("%s# %s\n", new Object[] {var2, var7});
            }
        }
    }

    private String collapsedtag(TreeMap var1, String var2, String var3)
    {
        String var5 = var2;
        String var6;

        for (Object var4 = var1.get(var2); this.comments.get(var3) == null; var3 = var3 + "." + var6)
        {
            if (var4 instanceof String)
            {
                return var5 + "=\"" + ((String)var4).replace("\"", "\\\"") + "\"";
            }

            if (var4 instanceof Integer)
            {
                return var5 + "=" + (Integer)var4;
            }

            var1 = (TreeMap)var4;

            if (var1.size() != 1)
            {
                return null;
            }

            var6 = (String)var1.firstKey();
            var5 = var5 + "." + var6;
            var4 = var1.get(var6);
        }

        return null;
    }

    private void savetag(PrintStream var1, TreeMap var2, String var3, String var4) throws IOException
    {
        Iterator var5 = var2.keySet().iterator();

        while (var5.hasNext())
        {
            String var6 = (String)var5.next();
            String var7 = var3 != null ? var3 + "." + var6 : var6;
            this.writecomment(var1, var4, (String)this.comments.get(var7));
            Object var8 = var2.get(var6);

            if (var8 instanceof String)
            {
                var1.printf("%s%s=\"%s\"\n", new Object[] {var4, var6, ((String)var8).replace("\"", "\\\"")});
            }
            else if (var8 instanceof Integer)
            {
                var1.printf("%s%s=%d\n", new Object[] {var4, var6, (Integer)var8});
            }
            else if (var8 instanceof TreeMap)
            {
                String var9 = this.collapsedtag(var2, var6, var7);

                if (var9 != null)
                {
                    var1.printf("%s%s\n", new Object[] {var4, var9});
                }
                else
                {
                    var1.printf("%s%s {\n", new Object[] {var4, var6});
                    this.savetag(var1, (TreeMap)var8, var7, var4 + "    ");
                    var1.printf("%s}\n\n", new Object[] {var4});
                }
            }
        }
    }

    public void saveFile(File var1)
    {
        try
        {
            FileOutputStream var2 = new FileOutputStream(var1);
            PrintStream var3 = new PrintStream(var2);
            this.writecomment(var3, "", this.filecomment);
            this.savetag(var3, this.contents, (String)null, "");
            var3.close();
        }
        catch (IOException var4)
        {
            var4.printStackTrace();
        }
    }

    private static void readtag(TreeMap var0, StreamTokenizer var1) throws IOException
    {
        label61:

        while (true)
        {
            if (var1.nextToken() != -1 && var1.ttype != 125)
            {
                if (var1.ttype == 10)
                {
                    continue;
                }

                if (var1.ttype != -3)
                {
                    throw new IllegalArgumentException("Parse error");
                }

                String var2 = var1.sval;
                TreeMap var4 = var0;

                while (true)
                {
                    TreeMap var3;
                    Object var5;

                    switch (var1.nextToken())
                    {
                        case 46:
                            var5 = var4.get(var2);

                            if (!(var5 instanceof TreeMap))
                            {
                                var3 = new TreeMap();
                                var4.put(var2, var3);
                                var4 = var3;
                            }
                            else
                            {
                                var4 = (TreeMap)var5;
                            }

                            var1.nextToken();

                            if (var1.ttype != -3)
                            {
                                throw new IllegalArgumentException("Parse error");
                            }

                            var2 = var1.sval;
                            break;

                        case 61:
                            var1.nextToken();

                            if (var1.ttype == -2)
                            {
                                var4.put(var2, Integer.valueOf((int)var1.nval));
                            }
                            else
                            {
                                if (var1.ttype != 34)
                                {
                                    throw new IllegalArgumentException("Parse error");
                                }

                                var4.put(var2, var1.sval);
                            }

                            var1.nextToken();

                            if (var1.ttype == 10)
                            {
                                continue label61;
                            }

                            throw new IllegalArgumentException("Parse error");

                        case 123:
                            var5 = var4.get(var2);

                            if (!(var5 instanceof TreeMap))
                            {
                                var3 = new TreeMap();
                                var4.put(var2, var3);
                                var4 = var3;
                            }
                            else
                            {
                                var4 = (TreeMap)var5;
                            }

                            readtag(var4, var1);
                            var1.nextToken();

                            if (var1.ttype == 10)
                            {
                                continue label61;
                            }

                            throw new IllegalArgumentException("Parse error");

                        default:
                            throw new IllegalArgumentException("Parse error");
                    }
                }
            }

            return;
        }
    }

    public static TagFile loadFile(File var0)
    {
        TagFile var1 = new TagFile();

        try
        {
            FileInputStream var2 = new FileInputStream(var0);
            var1.readStream(var2);
        }
        catch (IOException var3)
        {
            var3.printStackTrace();
        }

        return var1;
    }

    public void readFile(File var1)
    {
        try
        {
            FileInputStream var2 = new FileInputStream(var1);
            this.readStream(var2);
        }
        catch (IOException var3)
        {
            var3.printStackTrace();
        }
    }

    public void readStream(InputStream var1)
    {
        try
        {
            BufferedReader var2 = new BufferedReader(new InputStreamReader(var1));
            StreamTokenizer var3 = new StreamTokenizer(var2);
            var3.commentChar(35);
            var3.eolIsSignificant(true);
            var3.lowerCaseMode(false);
            var3.parseNumbers();
            var3.quoteChar(34);
            var3.ordinaryChar(61);
            var3.ordinaryChar(123);
            var3.ordinaryChar(125);
            var3.ordinaryChar(46);
            readtag(this.contents, var3);
            var1.close();
        }
        catch (IOException var4)
        {
            var4.printStackTrace();
        }
    }

    public Query query(String var1)
    {
        return new Query(this, var1, (TagFile)null);
    }

    public class Query implements Iterable
    {
        String[] pattern;

        final TagFile this$0;

        private Query(TagFile var1, String var2)
        {
            this.this$0 = var1;
            this.pattern = var2.split("\\.");
        }

        public Iterator iterator()
        {
            return new QueryIterator(this, (TagFile)null);
        }

        Query(TagFile var1, String var2, TagFile var3)
        {
            this(var1, var2);
        }
    }

    public class QueryIterator implements Iterator
    {
        ArrayList path;
        String lastentry;

        final Query this$1;

        private QueryIterator(Query var1)
        {
            this.this$1 = var1;
            this.path = new ArrayList();
            TreeMap var2 = var1.this$0.contents;
            Object var3 = null;

            if (!this.step0(0, var1.this$0.contents, ""))
            {
                this.step();
            }
        }

        private void step()
        {
            while (true)
            {
                if (this.path != null)
                {
                    if (!this.step1())
                    {
                        continue;
                    }

                    return;
                }

                return;
            }
        }

        private boolean step1()
        {
            QueryEntry var1 = (QueryEntry)this.path.get(this.path.size() - 1);

            if (!var1.iter.hasNext())
            {
                this.path.remove(this.path.size() - 1);

                if (this.path.size() == 0)
                {
                    this.path = null;
                }

                return false;
            }
            else
            {
                String var2 = (String)var1.iter.next();
                String var3 = var1.path.equals("") ? var2 : var1.path + "." + var2;

                if (var1.lvl == this.this$1.pattern.length - 1)
                {
                    this.lastentry = var3;
                    return true;
                }
                else
                {
                    Object var4 = var1.tag.get(var2);
                    return !(var4 instanceof TreeMap) ? false : this.step0(var1.lvl + 1, (TreeMap)var4, var3);
                }
            }
        }

        private boolean step0(int var1, TreeMap var2, String var3)
        {
            int var4 = var1;

            while (true)
            {
                if (var4 < this.this$1.pattern.length)
                {
                    if (this.this$1.pattern[var4].equals("%"))
                    {
                        QueryEntry var6 = new QueryEntry((TagFile)null);
                        var6.path = var3;
                        var6.tag = var2;
                        var6.lvl = var4;
                        var6.iter = var2.keySet().iterator();
                        this.path.add(var6);
                        return false;
                    }

                    Object var5 = var2.get(this.this$1.pattern[var4]);

                    if (var3.equals(""))
                    {
                        var3 = this.this$1.pattern[var4];
                    }
                    else
                    {
                        var3 = var3 + "." + this.this$1.pattern[var4];
                    }

                    if (var5 instanceof TreeMap)
                    {
                        var2 = (TreeMap)var5;
                        ++var4;
                        continue;
                    }

                    if (var4 == this.this$1.pattern.length - 1)
                    {
                        this.lastentry = var3;
                        return true;
                    }
                }

                this.path.remove(this.path.size() - 1);

                if (this.path.size() == 0)
                {
                    this.path = null;
                }

                return false;
            }
        }

        public boolean hasNext()
        {
            return this.path != null;
        }

        public String next()
        {
            String var1 = this.lastentry;
            this.step();
            return var1;
        }

        public void remove() {}

        QueryIterator(Query var1, TagFile var2)
        {
            this(var1);
        }
    }

    class QueryEntry
    {
        public TreeMap tag;
        public Iterator iter;
        public String path;
        int lvl;

        private QueryEntry() {}

        QueryEntry(TagFile var1)
        {
            this();
        }
    }
}
