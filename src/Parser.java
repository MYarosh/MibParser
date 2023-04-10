import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Parser{

    private FileReader fileReader = null;
    private FileWriter fileWriter = null;
    private final ArrayList<PropertyBlock> properties = new ArrayList<PropertyBlock>();
    private final ArrayList<TypeBlock> types = new ArrayList<>();

    public void parse(String filename){
        try{
            PropertyBlock start = new PropertyBlock("eNodeB","","","",
                    "eNodeB mib","mb4400-mib", ".1.3.6.1.4.1.1264.2.3.241.1");
            properties.add(start);
            fileReader = new FileReader(filename);
            findStart();
            boolean flag = true;
            PropertyBlock pb;
            while (flag){
                pb = readBlock();
                if ((pb!=null)&&(pb.getOid().equals(".1.3.6.1.4.1.1264.2.3.241.1.5.33.1.17"))) {
                    flag = false;
                }
                System.out.println();
                //System.out.println(pb);
            }
            fileWriter = new FileWriter("eNB-MIB.csv", false);
            for (PropertyBlock property:
                 properties) {
                if ((property.getSyntax() != null)&&(!property.getSyntax().equals(""))){
                    write(property);
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void parseTypes(String filename){
        try{
            fileReader = new FileReader(filename);
            TypeBlock tb = new TypeBlock();
            tb.setName(findStartTypes());
            boolean flag = true;
            while (flag){
                tb = new TypeBlock();
                tb = readTypeBlock(tb);
                if (tb.getName().equals("JitterHandlingOption")) {
                    flag = false;
                }
                //System.out.println(tb.getName());
                //System.out.println(tb);
            }
            fileWriter = new FileWriter("types.csv", false);
            for (TypeBlock typeBlock:
                    types) {
                System.out.println(typeBlock.getName());
                writeType(typeBlock);
            }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeType(TypeBlock typeBlock) throws IOException {
        fileWriter.write('"' + typeBlock.getName()+ '"'+","+
                '"' + typeBlock.getType()+ '"'+","+
                '"' + typeBlock.getValues().get(0)+ '"'+","+
                '"' + typeBlock.getDescription()+ '"'+"\n");
        for (int i=1; i < typeBlock.getValues().size(); i++){
            fileWriter.write("\"\"" +','+'"'+'"'+','+'"'+typeBlock.getValues().get(i)+'"'+','+'"'+'"'+"\n");
        }
        fileWriter.write("\"\",\"\",\"\",\"\"\n");

        System.out.println('"' + typeBlock.getName()+ '"'+","+
                '"' + typeBlock.getType()+ '"'+","+
                '"' + typeBlock.getValues().get(0)+ '"'+","+
                '"' + typeBlock.getDescription()+ '"'+"\n");
        for (int i=1; i < typeBlock.getValues().size(); i++){
            System.out.println("\"\"" + "," + '"' + '"' + "," + '"'+typeBlock.getValues().get(i)+'"'+','+'"'+'"'+"\n");
        }
    }

    private TypeBlock readTypeBlock() throws IOException {
        return readTypeBlock(new TypeBlock());
    }

    private TypeBlock readTypeBlock(TypeBlock typeBlock) throws IOException {
        if ((typeBlock.getName() != null)&&(typeBlock.getName().equals("eNodeBAttributes"))){
            return typeBlock;
        }
        String word;
        typeBlock.setName(readWord());
        if ((typeBlock.getName().toCharArray()[0]=='-')&&(typeBlock.getName().toCharArray()[1]=='-')){
            readLine();
            return typeBlock;
        }
        readLine();
        while(!(word = readWord()).equals("SYNTAX")){
            if (word.equals("DESCRIPTION")){
                typeBlock.setDescription(readDescription());
            }
        }
        typeBlock.setType(readWord());
        readWord();
        while (!(word = readWord()).equals("}")){
            if (word.toCharArray()[word.length()-1]==',') word = word.substring(0,word.length()-1);
            typeBlock.addValue(word);
        }
        types.add(typeBlock);
        return typeBlock;
    }

    private String findStartTypes() throws IOException {
        String s;
        while (!(s = readWord()).equals("MIBs"));
        return s;
    }

    private String readDescription() throws IOException {
        int ch, oldch = '"';
        StringBuilder description = new StringBuilder("");
        while ((ch = (char) fileReader.read())!='"');
        while ((ch = (char) fileReader.read())!='"'){
            if (((oldch!=ch)||(ch!=32))&&(ch!=9)&&(ch!=10)&&(ch!=13))
                description.append((char) ch);
            oldch = ch;
        }
        return description.toString();
    }

    private void write(PropertyBlock property) throws IOException {
        fileWriter.write('"' + property.getName()+ '"'+","+
                '"' + property.getOid()+ '"'+","+
                '"' + property.getSyntax()+ '"'+","+
                '"' + property.getDefval()+ '"'+","+
                '"' + property.getAccess()+ '"'+","+
                '"' + property.getParent()+ '"'+","+
                '"' + property.getDescription()+ '"'+"\n");
        System.out.println('"' + property.getName()+ '"'+","+
                '"' + property.getOid()+ '"'+","+
                '"' + property.getSyntax()+ '"'+","+
                '"' + property.getDefval()+ '"'+","+
                '"' + property.getAccess()+ '"'+","+
                '"' + property.getParent()+ '"'+","+
                '"' + property.getDescription()+ '"');
        /*System.out.println('"' + property.getName()+ '"'+","+
                '"' + property.getOid()+ '"'+"\n"+
                '"' + property.getParent()+ '"'+","+
                '"' + property.getSyntax()+ '"'+"\n"+
                '"' + property.getDefval()+ '"'+","+
                '"' + property.getAccess()+ '"'+"\n"+
                '"' + property.getDescription()+ '"'+","+'"'+'"'+"\n"+
                '"'+'"'+","+'"'+'"'+"\n");*/
        //System.out.println();
    }

    private String readWord() throws IOException {
        return readWord("");
    }

    private String readWord(String st) throws IOException {
        StringBuilder word = new StringBuilder(st);
        int ch;
        while (!(((ch=fileReader.read())!=32)&&(ch!=-1)&&(ch!=13)&&(ch!=10)&&(ch!=9)));
        word.append((char)ch);
        //System.out.println(ch);
        while (((ch=fileReader.read())!=32)&&(ch!=-1)&&(ch!=13)&&(ch!=10)&&(ch!=9)){
            //System.out.println(ch);
            word.append((char) ch);
        }
        return word.toString();
    }

    private String readLine() throws IOException {
        StringBuilder line = new StringBuilder("");
        int ch;
        while (!(((ch=fileReader.read())!=32)&&(ch!=-1)&&(ch!=13)&&(ch!=10)&&(ch!=9)));
        line.append((char)ch);
        while (((ch=fileReader.read())!=13)&&(ch!=-1)&&(ch!=10)){
            //System.out.println(ch);
            line.append((char) ch);
        }
        return line.toString();
    }

    private void findStart() throws IOException {
        StringBuilder start = new StringBuilder("");
        int ch;
        int j=0;
        while (!start.toString().equals("Section 4. ")) {
            start = new StringBuilder("");
            while ((char)(ch=fileReader.read())!='S');
            start.append((char) ch);
            for (int i = 0; i < 10; ++i) {
                start.append((char) fileReader.read());
            }
        }
        readWord();
        readWord();
        readWord();
        readWord();
    }

    private PropertyBlock readBlock() throws IOException {
        PropertyBlock property = new PropertyBlock();
        property.setName(readWord());
        String word = null;
        if ((property.getName().toCharArray()[0]=='-')&&(property.getName().toCharArray()[1]=='-')){
            readLine();
            return null;
        }
        if (readWord().equals("::=")){
            while(!readWord().equals("}"));
            return null;
        }
        while (!((word = readWord()).equals("::="))){
            if ((word.length()>1)&&(word.toCharArray()[0]=='-')&&(word.toCharArray()[1]=='-')) continue;
            //System.out.println(word);
            if (word.equals("SYNTAX")){
                property.setSyntax(readLine());
            }
            if (word.equals("DEFVAL")){
                int ch;
                while ((ch = (char) fileReader.read())!='{');
                property.setDefval(readWord());
                readLine();
            }
            if (word.equals("MAX-ACCESS")){
                property.setAccess(readWord());
            }
            if (word.equals("DESCRIPTION")){
                property.setDescription(readDescription());
            }
        }
        readWord();
        //System.out.println(readWord());
        property.setParent(readWord());
        String oid = null;
        for (PropertyBlock pb : properties){
            if (pb.getName().equals(property.getParent())){
                oid = pb.getOid();
            }
        }
        oid += ("." + readWord());
        property.setOid(oid);
        properties.add(property);
        readWord();
        return property;
    }
}
