package com.kakapo.model

enum class Currency(
    val languageCode: String,
    val countryCode: String,
    val countryName: String,
    val baseMultiplier: Int,
    val currencySymbol: String
) {

    AED("ar", "AE", "United Arab Emirates", 1, "د.إ"),
    AFN("fa", "AF", "Afghanistan", 1, "؋"),
    ALL("sq", "AL", "Albania", 1, "L"),
    AMD("hy", "AM", "Armenia", 1, "֏"),
    ANG("nl", "AN", "Netherlands Antilles", 1, "ƒ"),
    AOA("pt", "AO", "Angola", 1, "Kz"),
    ARS("es", "AR", "Argentina", 1, "$"),
    AUD("en", "AU", "Australia", 1, "$"),
    AWG("nl", "AW", "Aruba", 1, "ƒ"),
    AZN("az", "AZ", "Azerbaijan", 1, "₼"),
    BAM("bs", "BA", "Bosnia-Herzegovina", 1, "KM"),
    BBD("en", "BB", "Barbados", 1, "$"),
    BDT("bn", "BD", "Bangladesh", 1, "৳"),
    BGN("bg", "BG", "Bulgaria", 1, "лв"),
    BHD("ar", "BH", "Bahrain", 1, ".د.ب"),
    BIF("fr", "BI", "Burundi", 100, "FBu"),
    BMD("en", "BM", "Bermuda", 1, "$"),
    BND("ms", "BN", "Brunei", 1, "$"),
    BOB("es", "BO", "Bolivia", 1, "Bs."),
    BRL("pt", "BR", "Brazil", 1, "R$"),
    BSD("en", "BS", "Bahamas", 1, "$"),
    BTN("dz", "BT", "Bhutan", 1, "Nu."),
    BWP("en", "BW", "Botswana", 1, "P"),
    BYN("be", "BY", "Belarus", 1, "Br"),
    BZD("en", "BZ", "Belize", 1, "$"),
    CAD("en", "CA", "Canada", 1, "$"),
    CDF("fr", "CD", "Congo", 1000, "FC"),
    CHF("de", "CH", "Switzerland", 1, "CHF"),
    CLP("es", "CL", "Chile", 1, "$"),
    CNY("zh", "CN", "China", 1, "¥"),
    COP("es", "CO", "Colombia", 1, "$"),
    CRC("es", "CR", "Costa Rica", 1, "₡"),
    CUP("es", "CU", "Cuba", 1, "$"),
    CVE("pt", "CV", "Cape Verde", 1, "$"),
    CZK("cs", "CZ", "Czech Republic", 1, "Kč"),
    DJF("fr", "DJ", "Djibouti", 100, "Fdj"),
    DKK("da", "DK", "Denmark", 1, "kr"),
    DOP("es", "DO", "Dominican Republic", 1, "RD$"),
    DZD("ar", "DZ", "Algeria", 1, "د.ج"),
    EGP("ar", "EG", "Egypt", 1, "£"),
    ERN("ti", "ER", "Eritrea", 1, "Nfk"),
    ETB("am", "ET", "Ethiopia", 1, "Br"),
    EUR("de", "EU", "European Union", 1, "€"),
    FJD("en", "FJ", "Fiji", 1, "$"),
    FKP("en", "FK", "Falkland Islands", 1, "£"),
    FOK("fo", "FO", "Faroe Islands", 1, "kr"),
    GBP("en", "GB", "United Kingdom", 1, "£"),
    GEL("ka", "GE", "Georgia", 1, "₾"),
    GGP("en", "GG", "Guernsey", 1, "£"),
    GHS("en", "GH", "Ghana", 1, "₵"),
    GIP("en", "GI", "Gibraltar", 1, "£"),
    GMD("en", "GM", "Gambia", 1, "D"),
    GNF("fr", "GN", "Guinea", 100, "FG"),
    GTQ("es", "GT", "Guatemala", 1, "Q"),
    GYD("en", "GY", "Guyana", 1, "$"),
    HKD("zh", "HK", "Hong Kong", 1, "$"),
    HNL("es", "HN", "Honduras", 1, "L"),
    HRK("hr", "HR", "Croatia", 1, "kn"),
    HTG("fr", "HT", "Haiti", 1, "G"),
    HUF("hu", "HU", "Hungary", 1, "Ft"),
    IDR("id", "ID", "Indonesia", 100, "Rp"),
    ILS("he", "IL", "Israel", 1, "₪"),
    IMP("en", "IM", "Isle of Man", 1, "£"),
    INR("hi", "IN", "India", 1, "₹"),
    IQD("ar", "IQ", "Iraq", 1, "ع.د"),
    IRR("fa", "IR", "Iran", 100, "﷼"),
    ISK("is", "IS", "Iceland", 1, "kr"),
    JEP("en", "JE", "Jersey", 1, "£"),
    JMD("en", "JM", "Jamaica", 1, "J$"),
    JOD("ar", "JO", "Jordan", 1, "د.ا"),
    JPY("ja", "JP", "Japan", 1, "¥"),
    KES("en", "KE", "Kenya", 1, "KSh"),
    KGS("ky", "KG", "Kyrgyzstan", 1, "с"),
    KHR("km", "KH", "Cambodia", 1, "៛"),
    KID("en", "KI", "Kiribati", 1, "$"),
    KMF("fr", "KM", "Comoros", 1000, "CF"),
    KRW("ko", "KR", "South Korea", 1, "₩"),
    KWD("ar", "KW", "Kuwait", 1000, "د.ك"),
    KYD("en", "KY", "Cayman Islands", 1, "$"),
    KZT("kk", "KZ", "Kazakhstan", 1, "₸"),
    LAK("lo", "LA", "Laos", 1000, "₭"),
    LBP("ar", "LB", "Lebanon", 1, "ل.ل"),
    LKR("si", "LK", "Sri Lanka", 1, "Rs"),
    LRD("en", "LR", "Liberia", 1, "$"),
    LSL("en", "LS", "Lesotho", 1, "M"),
    LYD("ar", "LY", "Libya", 1000, "ل.د"),
    MAD("ar", "MA", "Morocco", 1000, "د.م."),
    MDL("ro", "MD", "Moldova", 1, "L"),
    MGA("mg", "MG", "Madagascar", 100, "Ar"),
    MKD("mk", "MK", "North Macedonia", 1, "ден"),
    MMK("my", "MM", "Myanmar", 1, "K"),
    MNT("mn", "MN", "Mongolia", 1, "₮"),
    MOP("zh", "MO", "Macau", 1, "MOP$"),
    MRU("ar", "MR", "Mauritania", 1, "UM"),
    MUR("en", "MU", "Mauritius", 1, "₨"),
    MVR("dv", "MV", "Maldives", 1, "Rf"),
    MWK("en", "MW", "Malawi", 1, "MK"),
    MXN("es", "MX", "Mexico", 1, "$"),
    MYR("ms", "MY", "Malaysia", 1, "RM"),
    MZN("pt", "MZ", "Mozambique", 1, "MT"),
    NAD("en", "NA", "Namibia", 1, "$"),
    NGN("en", "NG", "Nigeria", 1, "₦"),
    NIO("es", "NI", "Nicaragua", 1, "C$"),
    NOK("no", "NO", "Norway", 1, "kr"),
    NPR("ne", "NP", "Nepal", 1, "₨"),
    NZD("en", "NZ", "New Zealand", 1, "$"),
    OMR("ar", "OM", "Oman", 1000, "ر.ع."),
    PAB("es", "PA", "Panama", 1, "B/."),
    PEN("es", "PE", "Peru", 1, "S/."),
    PGK("en", "PG", "Papua New Guinea", 1, "K"),
    PHP("fil", "PH", "Philippines", 1, "₱"),
    PKR("ur", "PK", "Pakistan", 1, "₨"),
    PLN("pl", "PL", "Poland", 1, "zł"),
    PYG("es", "PY", "Paraguay", 1, "₲"),
    QAR("ar", "QA", "Qatar", 1000, "ر.ق"),
    RON("ro", "RO", "Romania", 1, "lei"),
    RSD("sr", "RS", "Serbia", 1, "дин."),
    RUB("ru", "RU", "Russia", 1, "₽"),
    RWF("rw", "RW", "Rwanda", 1000, "FRw"),
    SAR("ar", "SA", "Saudi Arabia", 1000, "ر.س"),
    SBD("en", "SB", "Solomon Islands", 1, "$"),
    SCR("en", "SC", "Seychelles", 1, "₨"),
    SDG("ar", "SD", "Sudan", 1, "ج.س."),
    SEK("sv", "SE", "Sweden", 1, "kr"),
    SGD("en", "SG", "Singapore", 1, "$"),
    SHP("en", "SH", "Saint Helena", 1, "£"),
    SLL("en", "SL", "Sierra Leone", 1, "Le"),
    SOS("so", "SO", "Somalia", 1, "Sh"),
    SRD("nl", "SR", "Suriname", 1, "$"),
    SSP("en", "SS", "South Sudan", 1, "£"),
    STN("pt", "ST", "São Tomé and Príncipe", 1, "Db"),
    SYP("ar", "SY", "Syria", 1, "£"),
    SZL("en", "SZ", "Eswatini", 1, "E"),
    THB("th", "TH", "Thailand", 1, "฿"),
    TJS("tg", "TJ", "Tajikistan", 1, "SM"),
    TMT("tk", "TM", "Turkmenistan", 1, "T"),
    TND("ar", "TN", "Tunisia", 1, "د.ت"),
    TOP("to", "TO", "Tonga", 1, "T$"),
    TRY("tr", "TR", "Turkey", 1, "₺"),
    TTD("en", "TT", "Trinidad and Tobago", 1, "$"),
    TVD("en", "TV", "Tuvalu", 1, "$"),
    TZS("sw", "TZ", "Tanzania", 1, "Sh"),
    UAH("uk", "UA", "Ukraine", 1, "₴"),
    UGX("en", "UG", "Uganda", 1, "USh"),
    USD("en", "US", "United States", 1, "$"),
    UYU("es", "UY", "Uruguay", 1, "\$U"),
    UZS("uz", "UZ", "Uzbekistan", 1, "soʻm"),
    VES("es", "VE", "Venezuela", 1, "Bs."),
    VND("vi", "VN", "Vietnam", 1, "₫"),
    VUV("bi", "VU", "Vanuatu", 1, "VT"),
    WST("sm", "WS", "Samoa", 1, "WS$"),
    XAF("fr", "CM", "Central African States", 1, "FCFA"),
    XCD("en", "AG", "East Caribbean States", 1, "$"),
    XOF("fr", "SN", "West African States", 1, "CFA"),
    XPF("fr", "PF", "French Polynesia", 1, "₣"),
    YER("ar", "YE", "Yemen", 1000, "﷼"),
    ZAR("en", "ZA", "South Africa", 1, "R"),
    ZMW("en", "ZM", "Zambia", 1, "ZK"),
    ZWL("en", "ZW", "Zimbabwe", 1, "Z$");

    companion object {
        fun fromCountryCode(countryCode: String): Currency? {
            return entries.find { it.countryCode.equals(countryCode, ignoreCase = true) }
        }

        fun fromLanguageCode(languageCode: String): Currency? {
            return entries.find { it.languageCode.equals(languageCode, ignoreCase = true) }
        }
    }
}

fun Int.asCurrency(): Currency {
    return Currency.entries.find { it.ordinal == this } ?: Currency.IDR
}