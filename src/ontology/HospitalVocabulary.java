package ontology;

public interface HospitalVocabulary {
    // MEDICO
    public static final String MEDICO = "medico";
    public static final String MEDICO_NOME = "nome";
    public static final String MEDICO_CRM = "CRM";
    public static final String MEDICO_ESPECIALIDADE = "especialidade";

    // PACIENTE
    public static final String PACIENTE = "paciente";
    public static final String PACIENTE_NOME = "nome";
    public static final String PACIENTE_CPF = "CPF";

    // CONSULTA
    public static final String CONSULTA = "Consulta";
    public static final String CONSULTA_SALA = "sala";
    public static final String CONSULTA_ID = "consultaID";
    public static final String CONSULTA_DATA = "data";

    // MARCAR_CONSULTA
    public static final String MARCAR_CONSULTA = "MarcarConsulta";
    public static final String MARCAR_CONSULTA_TIPO = "tipo";
    public static final String MARCAR_CONSULTA_ID = "amount";

    // PAGAR_CONSULTA
    public static final String PAGAR_CONSULTA = "PagarConsulta";
    public static final String PAGAR_CONSULTA_PRECO = "preço";
    public static final String PAGAR_CONSULTA_ID = "consultaID";
}