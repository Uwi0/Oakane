package com.kakapo.model

enum class Currency(val languageCode: String, val countryCode: String, val countryName: String) {

    AED("ar", "AE", "United Arab Emirates"), // Dirham
    AFN("fa", "AF", "Afghanistan"), // Afghani
    ALL("sq", "AL", "Albania"), // Lek
    AMD("hy", "AM", "Armenia"), // Dram
    ANG("nl", "AN", "Netherlands Antilles"), // Guilder
    AOA("pt", "AO", "Angola"), // Kwanza
    ARS("es", "AR", "Argentina"), // Peso
    AUD("en", "AU", "Australia"), // Dollar
    AWG("nl", "AW", "Aruba"), // Florin
    AZN("az", "AZ", "Azerbaijan"), // Manat
    BAM("bs", "BA", "Bosnia-Herzegovina"), // Convertible Mark
    BBD("en", "BB", "Barbados"), // Dollar
    BDT("bn", "BD", "Bangladesh"), // Taka
    BGN("bg", "BG", "Bulgaria"), // Lev
    BHD("ar", "BH", "Bahrain"), // Dinar
    BIF("fr", "BI", "Burundi"), // Franc
    BMD("en", "BM", "Bermuda"), // Dollar
    BND("ms", "BN", "Brunei"), // Dollar
    BOB("es", "BO", "Bolivia"), // Boliviano
    BRL("pt", "BR", "Brazil"), // Real
    BSD("en", "BS", "Bahamas"), // Dollar
    BTN("dz", "BT", "Bhutan"), // Ngultrum
    BWP("en", "BW", "Botswana"), // Pula
    BYN("be", "BY", "Belarus"), // Ruble
    BZD("en", "BZ", "Belize"), // Dollar
    CAD("en", "CA", "Canada"), // Dollar
    CDF("fr", "CD", "Congo"), // Franc
    CHF("de", "CH", "Switzerland"), // Swiss Franc
    CLP("es", "CL", "Chile"), // Peso
    CNY("zh", "CN", "China"), // Yuan
    COP("es", "CO", "Colombia"), // Peso
    CRC("es", "CR", "Costa Rica"), // Colón
    CUP("es", "CU", "Cuba"), // Peso
    CVE("pt", "CV", "Cape Verde"), // Escudo
    CZK("cs", "CZ", "Czech Republic"), // Koruna
    DJF("fr", "DJ", "Djibouti"), // Franc
    DKK("da", "DK", "Denmark"), // Krone
    DOP("es", "DO", "Dominican Republic"), // Peso
    DZD("ar", "DZ", "Algeria"), // Dinar
    EGP("ar", "EG", "Egypt"), // Pound
    ERN("ti", "ER", "Eritrea"), // Nakfa
    ETB("am", "ET", "Ethiopia"), // Birr
    EUR("de", "EU", "European Union"), // Euro
    FJD("en", "FJ", "Fiji"), // Dollar
    FKP("en", "FK", "Falkland Islands"), // Pound
    FOK("fo", "FO", "Faroe Islands"), // Króna
    GBP("en", "GB", "United Kingdom"), // Pound
    GEL("ka", "GE", "Georgia"), // Lari
    GGP("en", "GG", "Guernsey"), // Pound
    GHS("en", "GH", "Ghana"), // Cedi
    GIP("en", "GI", "Gibraltar"), // Pound
    GMD("en", "GM", "Gambia"), // Dalasi
    GNF("fr", "GN", "Guinea"), // Franc
    GTQ("es", "GT", "Guatemala"), // Quetzal
    GYD("en", "GY", "Guyana"), // Dollar
    HKD("zh", "HK", "Hong Kong"), // Dollar
    HNL("es", "HN", "Honduras"), // Lempira
    HRK("hr", "HR", "Croatia"), // Kuna
    HTG("fr", "HT", "Haiti"), // Gourde
    HUF("hu", "HU", "Hungary"), // Forint
    IDR("id", "ID", "Indonesia"), // Rupiah
    ILS("he", "IL", "Israel"), // New Shekel
    IMP("en", "IM", "Isle of Man"), // Pound
    INR("hi", "IN", "India"), // Rupee
    IQD("ar", "IQ", "Iraq"), // Dinar
    IRR("fa", "IR", "Iran"), // Rial
    ISK("is", "IS", "Iceland"), // Krona
    JEP("en", "JE", "Jersey"), // Pound
    JMD("en", "JM", "Jamaica"), // Dollar
    JOD("ar", "JO", "Jordan"), // Dinar
    JPY("ja", "JP", "Japan"), // Yen
    KES("en", "KE", "Kenya"), // Shilling
    KGS("ky", "KG", "Kyrgyzstan"), // Som
    KHR("km", "KH", "Cambodia"), // Riel
    KID("en", "KI", "Kiribati"), // Dollar
    KMF("fr", "KM", "Comoros"), // Franc
    KRW("ko", "KR", "South Korea"), // Won
    KWD("ar", "KW", "Kuwait"), // Dinar
    KYD("en", "KY", "Cayman Islands"), // Dollar
    KZT("kk", "KZ", "Kazakhstan"), // Tenge
    LAK("lo", "LA", "Laos"), // Kip
    LBP("ar", "LB", "Lebanon"), // Pound
    LKR("si", "LK", "Sri Lanka"), // Rupee
    LRD("en", "LR", "Liberia"), // Dollar
    LSL("en", "LS", "Lesotho"), // Loti
    LYD("ar", "LY", "Libya"), // Dinar
    MAD("ar", "MA", "Morocco"), // Dirham
    MDL("ro", "MD", "Moldova"), // Leu
    MGA("mg", "MG", "Madagascar"), // Ariary
    MKD("mk", "MK", "North Macedonia"), // Denar
    MMK("my", "MM", "Myanmar"), // Kyat
    MNT("mn", "MN", "Mongolia"), // Tögrög
    MOP("zh", "MO", "Macau"), // Pataca
    MRU("ar", "MR", "Mauritania"), // Ouguiya
    MUR("en", "MU", "Mauritius"), // Rupee
    MVR("dv", "MV", "Maldives"), // Rufiyaa
    MWK("en", "MW", "Malawi"), // Kwacha
    MXN("es", "MX", "Mexico"), // Peso
    MYR("ms", "MY", "Malaysia"), // Ringgit
    MZN("pt", "MZ", "Mozambique"), // Metical
    NAD("en", "NA", "Namibia"), // Dollar
    NGN("en", "NG", "Nigeria"), // Naira
    NIO("es", "NI", "Nicaragua"), // Córdoba
    NOK("no", "NO", "Norway"), // Krone
    NPR("ne", "NP", "Nepal"), // Rupee
    NZD("en", "NZ", "New Zealand"), // Dollar
    OMR("ar", "OM", "Oman"), // Rial
    PAB("es", "PA", "Panama"), // Balboa
    PEN("es", "PE", "Peru"), // Sol
    PGK("en", "PG", "Papua New Guinea"), // Kina
    PHP("fil", "PH", "Philippines"), // Peso
    PKR("ur", "PK", "Pakistan"), // Rupee
    PLN("pl", "PL", "Poland"), // Zloty
    PYG("es", "PY", "Paraguay"), // Guarani
    QAR("ar", "QA", "Qatar"), // Riyal
    RON("ro", "RO", "Romania"), // Leu
    RSD("sr", "RS", "Serbia"), // Dinar
    RUB("ru", "RU", "Russia"), // Ruble
    RWF("rw", "RW", "Rwanda"), // Franc
    SAR("ar", "SA", "Saudi Arabia"), // Riyal
    SBD("en", "SB", "Solomon Islands"), // Dollar
    SCR("en", "SC", "Seychelles"), // Rupee
    SDG("ar", "SD", "Sudan"), // Pound
    SEK("sv", "SE", "Sweden"), // Krona
    SGD("en", "SG", "Singapore"), // Dollar
    SHP("en", "SH", "Saint Helena"), // Pound
    SLL("en", "SL", "Sierra Leone"), // Leone
    SOS("so", "SO", "Somalia"), // Shilling
    SRD("nl", "SR", "Suriname"), // Dollar
    SSP("en", "SS", "South Sudan"), // Pound
    STN("pt", "ST", "São Tomé and Príncipe"), // Dobra
    SYP("ar", "SY", "Syria"), // Pound
    SZL("en", "SZ", "Eswatini"), // Lilangeni
    THB("th", "TH", "Thailand"), // Baht
    TJS("tg", "TJ", "Tajikistan"), // Somoni
    TMT("tk", "TM", "Turkmenistan"),
    TND("ar", "TN", "Tunisia"), // Dinar
    TOP("to", "TO", "Tonga"), // Paʻanga
    TRY("tr", "TR", "Turkey"), // Lira
    TTD("en", "TT", "Trinidad and Tobago"), // Dollar
    TVD("en", "TV", "Tuvalu"), // Dollar
    TZS("sw", "TZ", "Tanzania"), // Shilling
    UAH("uk", "UA", "Ukraine"), // Hryvnia
    UGX("en", "UG", "Uganda"), // Shilling
    USD("en", "US", "United States"), // Dollar
    UYU("es", "UY", "Uruguay"), // Peso
    UZS("uz", "UZ", "Uzbekistan"), // So'm
    VES("es", "VE", "Venezuela"), // Bolívar
    VND("vi", "VN", "Vietnam"), // Dong
    VUV("bi", "VU", "Vanuatu"), // Vatu
    WST("sm", "WS", "Samoa"), // Tala
    XAF("fr", "CM", "Central African States"), // CFA Franc
    XCD("en", "AG", "East Caribbean States"), // Dollar
    XOF("fr", "SN", "West African States"), // CFA Franc
    XPF("fr", "PF", "French Polynesia"), // CFP Franc
    YER("ar", "YE", "Yemen"), // Rial
    ZAR("en", "ZA", "South Africa"), // Rand
    ZMW("en", "ZM", "Zambia"), // Kwacha
    ZWL("en", "ZW", "Zimbabwe"); // Dollar

    companion object {
        fun fromCountryCode(countryCode: String): Currency? {
            return entries.find { it.countryCode.equals(countryCode, ignoreCase = true) }
        }

        fun fromLanguageCode(languageCode: String): Currency? {
            return entries.find { it.languageCode.equals(languageCode, ignoreCase = true) }
        }
    }
}