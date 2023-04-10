public class PropertyBlock {
    private String name, syntax, defval, access, description, parent, oid;

    public PropertyBlock(String name, String syntax, String defval, String access,
                         String description, String parent, String oid){
        this.name = name;
        this.syntax = syntax;
        this.defval = defval;
        this.access = access;
        this.description = description;
        this.parent = parent;
        this.oid = oid;
    }

    public PropertyBlock(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSyntax() {
        return syntax;
    }

    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    public String getDefval() {
        return defval;
    }

    public void setDefval(String defval) {
        this.defval = defval;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String toString(){
        return "PropertyBlock :\n"+
                "Name : "+getName()+";\n"+
                "OID : "+getOid()+";\n"+
                "Syntax : "+getSyntax()+";\n"+
                "DefVal : "+getDefval()+";\n"+
                "Access : "+getAccess()+";\n"+
                "Parent : "+getParent()+";\n"+
                "Description : "+getDescription()+".\n";
    }
}
