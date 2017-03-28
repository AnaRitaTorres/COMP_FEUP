public TypeReference extends Reference {
    //usem esta anotação quando a propriedade no JSON é uma palavra reservada. aqui para não dar erro no Java digo que a minha variável
    @SerializedName("package")
    //chama-se "_package" MAS a propriedade que ele deve usar é mesmo "package"
	private String _package;	
}