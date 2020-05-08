package com.nymbus.newmodels;

import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.client.other.document.BaseDocument;
import com.nymbus.newmodels.client.other.document.IDType;

public class Sample {
    public static void main(String[] args) {
        /*Address address = new Address();
        address.setType(AddressType.ALTERNATE);
        address.setCountry(Country.UNITED_STATES);
        address.setCity("qwe");
        address.setAddress("qwe");
        address.setZipCode("1234567");

        Phone phone11 = new Phone();
        phone11.setPhoneType(PhoneType.MOBILE);
        phone11.setCountry(Country.UNITED_STATES);
        phone11.setPhoneNumber("1234567890");

        Phone phone12 = new Phone();
        phone12.setPhoneType(PhoneType.HOME);
        phone12.setCountry(Country.UNITED_STATES);
        phone12.setPhoneNumber("0987654321");

        Email email11 = new Email();
        email11.setEmailType(EmailType.PRIMARY);
        email11.setEmail("qwerty");

        Email email12 = new Email();
        email12.setEmailType(EmailType.ALTERNATE);
        email12.setEmail("asdfgh");

        OrganisationType organisationType = new Estate();
        organisationType.setName("qwe");
        organisationType.setTaxPayerIDType(TaxPayerIDType.INDIVIDUAL_SSN);
        organisationType.setTaxID("1234567");
        organisationType.setAddresses(Collections.singleton(address));

        OrganisationClientDetails clientDetails = new OrganisationClientDetails();
        clientDetails.setIndustry("qwe");
        clientDetails.setUserDefinedFields(new ArrayList<>(Arrays.asList("qwe1", "qwe2", "qwe3")));
        clientDetails.setPhones(new HashSet<>(Arrays.asList(phone11, phone12)));
        clientDetails.setEmails(new HashSet<>(Arrays.asList(email11, email12)));

        Document document = new BaseDocument();
        ((BaseDocument) document).setIdType(IDType.COMPANY_ID);


        Client client = new OrganisationClient();
        ((OrganisationClient) client).setOrganisationType(organisationType);
        ((OrganisationClient) client).setOrganisationClientDetails(clientDetails);
        client.setDocuments(Collections.singletonList(document));



        System.out.println(client);
        System.out.println("\n\n");



        Address address1 = new Address();
        address1.setType(AddressType.ALTERNATE);
        address1.setCountry(Country.UNITED_STATES);
        address1.setCity("qwe");
        address1.setAddress("qwe");
        address1.setZipCode("1234567");

        Phone phone21 = new Phone();
        phone21.setPhoneType(PhoneType.MOBILE);
        phone21.setCountry(Country.UNITED_STATES);
        phone21.setPhoneNumber("1234567890");

        Phone phone22 = new Phone();
        phone22.setPhoneType(PhoneType.HOME);
        phone22.setCountry(Country.UNITED_STATES);
        phone22.setPhoneNumber("0987654321");

        Email email21 = new Email();
        email21.setEmailType(EmailType.PRIMARY);
        email21.setEmail("qwerty");

        Email email22 = new Email();
        email22.setEmailType(EmailType.ALTERNATE);
        email22.setEmail("asdfgh");

        OrganisationType organisationType1 = new Estate();
        organisationType1.setName("qwe");
        organisationType1.setTaxPayerIDType(TaxPayerIDType.INDIVIDUAL_SSN);
        organisationType1.setTaxID("1234567");
        organisationType1.setAddresses(Collections.singleton(address1));

        OrganisationClientDetails clientDetails1 = new OrganisationClientDetails();
        clientDetails1.setIndustry("qwe");
        clientDetails1.setUserDefinedFields(new ArrayList<>(Arrays.asList("qwe1", "qwe2", "qwe3")));
        clientDetails1.setPhones(new HashSet<>(Arrays.asList(phone21, phone22)));
        clientDetails1.setEmails(new HashSet<>(Arrays.asList(email21, email22)));

        Document document1 = qwe();


        Client client1 = new OrganisationClient();
        ((OrganisationClient) client1).setOrganisationType(organisationType1);
        ((OrganisationClient) client1).setOrganisationClientDetails(clientDetails1);
        client1.setDocuments(Collections.singletonList(document1));

        System.out.println(client1);
        System.out.println("\n\n");

        System.out.println(client.equals(client1));*/

        /*OrganisationClientBuilder organisationClientBuilder = new OrganisationClientBuilder();
        organisationClientBuilder.setOrganisationTypeBuilder(new TrustAccountBuilder());
        System.out.println(organisationClientBuilder.buildClient());*/

        /*Transaction1 tr1 = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        System.out.println(tr1.toString());*/

        /*HoldInstruction instr = new InstructionConstructor(new HoldInstructionBuilder()).constructInstruction(HoldInstruction.class);
        System.out.println(instr.toString());*/

        // System.out.println(DateTime.getDaysBetweenTwoDates("04/10/2020", "07/04/2020"));

        // System.out.println(getCalculatedInterestAmount(100.00, 0.45, "04/16/2020", "07/16/2020"));

        /*ExtendedBalanceDataForCHKAcc balanceDataForCHKAcc = new ExtendedBalanceDataForCHKAcc();
        balanceDataForCHKAcc.setCurrentBalance(100);
        balanceDataForCHKAcc.setAverageBalance(100);
        balanceDataForCHKAcc.setCollectedBalance(100);
        balanceDataForCHKAcc.setAvailableBalance(100);
        System.out.println(balanceDataForCHKAcc.toString());
        balanceDataForCHKAcc.reduceAmount(50);
        System.out.println(balanceDataForCHKAcc.toString());*/

        /*IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());

        IndividualClient individualClient = individualClientBuilder.buildClient();

        OrganisationClientBuilder organisationClientBuilder = new  OrganisationClientBuilder();
        organisationClientBuilder.setOrganisationTypeBuilder(new CorporationBuilder());

        OrganisationClient organisationClient = organisationClientBuilder.buildClient();*/

        // System.out.println(WebAdminActions.webAdminTransactionActions().getTransactionUrl("123456"));

       /* System.out.println(CHKAccountData.getAtmDepositData("123456", "2404", "5000").toString());*/

        /*Actions.nonTellerTransactionActions().performDepositTransactions(5, "2706827068428111", "2404", "5000");*/

       /* System.out.println(DateTime.getDateWithFormat("08/24/2024", "MM/dd/yyyy", "yyMM"));*/

        System.out.println(DateTime.getMonthNumberByMonthName("August"));
    }

    private static BaseDocument qwe() {
        BaseDocument companyID = new BaseDocument();
        companyID.setIdType(IDType.COMPANY_ID);


        return companyID;
    }
}
