import java.util.*;

public class LongestDuplicateSubstring {

    public static String longestDupSubstring(String s) {
        if (s == null || s.length() == 0) return "";

        int left = 0;
        int right = s.length();
        String res = "";

        while (left <= right) {
            int mid = left + (right - left) / 2;
            String s1 = computeRepeatingSubstring(s, mid);
            if (s1 != null) {
                res = s1;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return res;
    }

    private static String computeRepeatingSubstring(String s, int k) {
        // A set to store the substrings of length k we've already seen.
        Set<String> seen = new HashSet<>();
        
        // Iterate through all possible starting positions for a substring of length k.
        for (int i = 0; i <= s.length() - k; i++) {
            String substring = s.substring(i, i + k);
            
            // If we can't add the substring to the set, it's because we've seen it before.
            if (!seen.add(substring)) {
                // This is our duplicate!
                return substring;
            }
        }
        
        // If we finish the loop, no duplicates of length k were found.
        return null;
    }

    static String computeRepeatingSubstringRollingHash(String s, int k) {
        if (k == 0) return "";
        if (k >= s.length()) return null;
        
        long prime = 31;
        long mod = 1_000_000_007L;
        
        long power = 1;
        for (int i = 0; i < k; i++) {
            power = (power * prime) % mod;
        }

        long currentHash = 0;
        for (int i = 0; i < k; ++i) {
            currentHash = (currentHash * prime + s.charAt(i)) % mod;
        }

        // --- CHANGE: Use a HashMap to store hashes and start indices ---
        HashMap<Long, List<Integer>> seen = new HashMap<>();
        seen.put(currentHash, new ArrayList<>(List.of(0)));

        for (int i = k; i < s.length(); ++i) {
            char leavingChar = s.charAt(i - k);
            char enteringChar = s.charAt(i);

            currentHash = (currentHash * prime - leavingChar * power + enteringChar) % mod;
            if (currentHash < 0) currentHash += mod;

            // --- COLLISION CHECK ---
            if (seen.containsKey(currentHash)) {
                String currentSubstring = s.substring(i - k + 1, i + 1);
                // Verify if any of the previous substrings with the same hash are identical.
                for (int startIndex : seen.get(currentHash)) {
                    if (s.substring(startIndex, startIndex + k).equals(currentSubstring)) {
                        return currentSubstring; // True duplicate found!
                    }
                }
            }
            
            // Add the current hash and its starting index to the map.
            seen.computeIfAbsent(currentHash, key -> new ArrayList<>()).add(i - k + 1);
        }
        return null;
    }



    public static void main(String[] args) {
        System.out.println(longestDupSubstring("banana"));
        System.out.println(longestDupSubstring("rabbit"));

        String s = "gzhvilxpjrgrrezatnuzihvaezrbvalaovljiscsnbmtrnthyijuxuehuiffeaknaywwhlfgfvrlbzbdpgszfpdetyyokkdocmexbvoklmvvbtslafxoepxlonexfinlirflmfjxtedkctiyvsvqknwxbumollkdbquysfvqexksoxrjzwzplmrkfozpumozednrycmrxkasvmiqprunuaaqqdijfxjulluuyxalmhglggemtbgimowlzwmbtgyezcuwsscxrofosjmeohhcwuhblbgbmrqwmifbitwzxkpzetbmjaqytnvnehtajurbkerzsekykgpzeojftklggqzlcgconzxntmfbehpjchsajpgrcnnnmhkudwvoashlcnltxglqrtwxybeyptgvrsvofmilpefzatucmhhbdwopxnyzqrovbkqslftylbiphfgvjddottgpbjkwikniijdxqrkxllnuxxkzpovuazkiebfkzfaosfdaxefkyevetouvpopcmqxdwnnejbsnexbpvlgxpgdmppzrhklmzioedkbdtyuxlyimogtyfsskuvurjdxxnjeptaacvooyfyvseeyuootkiexqnbyatybimqdkbmbedhyufdedhbvkdtdsqygzahnogadgbnmagtumobzsknihjasqfputkgtjrepwsqxpzbdpimjmqltkqajlmkxyfdpohvumbjmtkcoqbtpfbgrnyvtnxfbzxrrdcocbshyxlmqgmpwnpaskosvcpmnobagvbufmwdzbggemrmwqzvadkojmxljcvumpbwbexlkhfzbzijieimpmtyspeikpjjrcbthptzmxufrgbzpkhnwqmxplwwakhrepoknhanzgrtzcjqrcaovbkmvpbgqshfdtugenluyjfwpnackdnazrwuxguynnmgnxsrmgqsheanicnuupjsekkjbubmlzsowrwdmhcvnijixpbpkjxcddjhbucjjmhzbjngflezexzaamyyywabsavwdasergsxopvexfkyolcgjpopxlpmcokwfymfvkkeejlfihhnieplqutfwajcduajkfokgdhglqswawpvaeiofrlqushjlykcmaaezqylzfrmshjldjkwwlnfdjftwqsyczfnrdfywajhepujqpjhvdoktpvokhivjwzbvyifhxxjmtxitnvgebamsuwttixondcxqmabhaskvgpsoxgghkucgbwylxkdcmyyfmlwqdktvqsxeuvnsxlwiskhnmlemnyodjheovpccnndsjdpnsrxbrqmvhrmjqquiblrcmtpkhdenbsufplctuymidzbedsdqbsxpeuqllluxgwgesgevutvnrmxkqajulkyacqnqlxxmezzmnezwzkhxmxvooqirztzsfftbmahxltyoouhsvxaedsgacvyqwgninsmgdzwdirmsrjhpcxmcerivcnykalvvwrlxynmbnzwjyaebvdusydsolzsvwynlmoawydodomzfescurfjkxantfbsvoitdalpnmkysfjybknczgabfwcnpigzzufmrjquppcglavjpqdhbnategojhymfajimkfdsmadriphaiwcyrqzbxosflkvywauptbkowspvhknrdxuolqjnosnalniutvtsebvfqkqcxtrpgjhwdbcxmxqvrilbrfibweettocslcwkduuoszjhqefbkxcynplgdnwotnyqyeejzjesuxiapqssqrdoveodnbmsvuwimwcqamksazakdrzfflchmtyygramodlvnuxrtgmryzefuemfihgzrcnhwynbhjmwbeyaqoeskrkwrlpcdgvwgcuvrbpawmuicbthocynmtjiffxiiemmyczeyvixeernlfgslmmfpcytgcakmhelukknqxypbcbtkiizexvqptixetojnytvhukzyhjdnarwzzujjatczabhhsfiyjaxgypimlgrspixnwxtmghdhaivigwhbfqmmicuoolftlgvtwwxbpsrhqjmzdnkttzsvgjaloqlmdcsmcukmbmlqwqrvhvdrnwxscgbfibjvrcavzayjmjspnajmukdqczrxagwpojxztflhfykkabksoidyrhrclasmvcacgsdvzzrssmiirjjjvarbdyqxoytradmfdgqsknlucbiahxbhfsdcevclnnbiinjjxelaydqogirhfbsdkexygukrjqsmquvvyovsupxpksmuxxyvbdwgdnvswgazgfnjusxfmtotbzyppytrolmvfymvvwezxontpexhftejbxqosltqawtidhguuykdylvswranfklcncjgvnfwwqprhssyovhylcezvqrdtkudvrqqausrkeoihnerrkvpdsdtnbpltsllgqzifbfkpjmcmvztfdzrrdcwesuhtuxhxabmpnxybtknexscqltybkdkjlvaywngtpkqhyfasswozlzzoqtqofzqlrvshmhtfodoipsrkjbbdaztfvuhpgjfnrlajikjyvxvkhyllknsaqkzxrycpooyvhvnphbysuhfrxzyaltuakrzeasxyqdbkdsvlyngshuknxcdrytrhvogsrzmosyzbmnbadnpetagczhhocascojyzuzwukuxvgkfuepiakepykbvxaavayfawropobdtodhmfkmwdokrsoigeuzgxerksxepfncqykwttkotojmdffjoiznlgpiclacyuzjgkaqaijnummpotptguquzaykiszjczmzbstfatflgcraqavriundgkzrxnqyeqjuqdqstloxnnqctagyunytlxzedclzztzwegnighzixvmkfxabnxumvflmtmtdvfzmvtwduggyteagektibddntwdqetpodgzcmugxulcbfmthgqrgxneqctqbtaensknsggueoibzgvdascsstuoidvbscasigbzlkwkmtkwxdstxjweqfnnnihjfazysghlyefhptqaecggexrfuccetxyivqkoapvxwmyjdekotcszhcvfybutluikyqwhjxswrvloqwmpjsamylpbjjdnbaqitpwrwgwjalamcynojjqfguyutglvsyghggehlbzjkdfodfjebfvpskhhxcnegauwgtbmmnsmyerfaycecprwnvwqmuzdvsjjrffhatfhbhrbczmwkgnmzmqkgvipxfdtkriuekdktraebiqrxsphxkqdlbixxyulwhsfivkwqkiwkzihwphhglwuiwblqxctleyaiqphlblyorzutdxujtufktkgkkslnrvkeejzvizivljvvrvqnsydaotfcnzmrttbimakwphdniksoludxiogjpdmazbnohqrxlodjggmmjnsmzprxkgybjqilsgupnyehtkdcapdkcbcpcjgmzukwxmpntutlwljikjxppecreizqeqxuqolhrzsxfldfhyazoakuwrrpxltbsbczjzqjcuyhydxgstlydyasgykimeebpbpnshflijauctortfwjjfxwodmkkrcqkeoofunszmajfppjrvhbprwhrmsglpvsufwmnrolcwxmbvufodtdftlkuazylxwjaqcnvnathtzrxaxpbtpoqyedzwdazmwpygzmikwpbuvnqlejdumkdxzyxublcawwlykjgvhbhdlhpjiztbuvvqolwfaviwbnooekjncyaisibdldxydkdtpulquwmitdnpjgzdpmeprkbregvmgwhjmokhhftozfhhnyyqovuacmnmhclsdyhrnrdtvzfpxcpgnejjyozqkoqzacfabbfzrrokbcxzsfeqkcxkzfjjtqyiurfgtwittlzyvooxywevqqqabjzevlygqimwyrzcfvivndmdgliyovhybyklbcreqbpavyycgvxtsvcilnhrbwpjhbdpmsqyixiyzcywopztvzbabddxofmafxooumrzhfilyegilsupqljcfucgydpglurrwngwaajuopqqiknbykjtcoxkcanzxcsfkcwbmyxiebfkaojfqbyhsnhbqcsolmvoguhaomfvgtvfruajxttmheofzrdvwdifkvmgczbpsijewbfydwhibbavyrgbvruxyjxwyxxrmwnbkhmferwhbhbuurotcyupktkexnbzcskarsqgtmlhsezalnwkddgklhcaytncyfpsgrasgzxvqdyzfufqlkhcfykztcnypmangnuvkcfnfnjunnwdcxbtzvnhgrhqnduozvqbestwyxomfjuurrxvjwfusyqsgilqgihucrwiihmykpgjbwwkzwnagqngtvtufhhpcqpvypybzndnuxnuxcjuvvedglyjbiikrvhrmshsyvyilgitnccwmhenadmkrfvkyutcqrgvhszafvsgpplwndinjxsdvoygofjbuijmtojknwoukqgzqqaxaerzznpelookalbyjcjedekroepjpneruaihgxtunivhmdneljnbhhmlekeefocgfcantsfcystwhkksfoawfgauopzeskqumoavmbeawyylzgabsfsfdldzezvvdidoegzyobmdaeowshuwwxxaswczpaykvkyrwlixxkeonjjnzlrfpbgmrlvgryjqmbdvjhrrkjaqmkgpmtzxhyombreolaobjbixepcwfbzyrhjkvqvdwblxttprawmlrtikvukydhtobvatgvieoxqrbtagkbjwiuzyunjdquxmzcejeoowlupstmkqzfrguffffpizqlnighdpsaabcqtrxguhyhhzrrsdwlpgwyygbpudpxdxqgckmpvzjsnmhznhzkkceomwlrdnjjarasomfraiydswbzwvlygygbtfoawwsflhtgexbaeotfhfokiaxeerqecdwbkuoutdahpbvosbxjlpvgaubsfpgmppqmsffgfwcphthaawskscuwhbjvqthhxyfcvzkfismdeoguryafwdfstrndoqwjytbganmbpinjcnemijucqhekatenftxwycbylehctyxmwohhaqlilecxlwhjixzgstnpvqjqiedkvgcthaafzurjkxskymkateofvmewwalcmywtrlnhtpxoknmjrmajhiouxvytttuwbarlewegptlourwhpzejayoyzjygcnjhqmsgneuajfyyweditelealzjnwwdqnznmbheppnqttoechqmnrqgnhojehnwauotgkxqwswcyfmfefuycbzqutzpcxzeziwsewnihvidkyxupyjxowtpabjgincjaaxahbadxizimilhgqwbfasephsoncpyokqcxhwnqqmftgnzrpotemweubivjtlkefhefgiingkukpoqakkizzbyryqlthpwyiiwxtszdrucddvwkbvazbwwfyrsusdqzqlbbghmgogfkabaopenezmykvdabjnovrebhpanpguamvbswuebryanmaifxwwvxsqskmoqmbvrkwjgkkictbrwswnvbapgbiuxbnbwymyposzlwgklbckzzxwtxgpsxjlkfympahxftmarlqrleoinppthrdxiqasapmediekmxpwwgawbtlmgomejcxkpepeefojswvdlyoafwiwxdldnrhirgnevkgpzksxwzppmdvlwnsynwzhiizzgagjxbfmnyaxfdkwxxanxbmmbauvhbjetqkcexmyrlgyxnmjodmuwjrxaiyzwkdaaerexgqkvwbfvaoakofuikxiiilodglyzjxuhespifzeozjxwzurbyjclqiyjyowwokemiakbnjlpvgzaqqklnhbdaxldifunjtqcvubnpeetnhrdnpemjwzxuqwbfdkbjbkzbrdqjmdtqbnxsbqjmbmzctyrsbjobzfevvytuhsgwxgilffdasxhzhihfpiymqzptvssmvpayupggepznusyboilojxnotagbmewjfmjbdduwjrdnuooxkhtozramtlxaunvzczcjogzmchrivzpiieshwfeassayddsfhleqjdnloinszgbdjngjhecmgodpmowwlpvmcgsuuerzhezeyejhpnlvesiiudniyzyphmwnkhvreciyskiyletzqvjopzbfbtquixhbyoqywhncageqbiugvvnndhfoayehaofbucmonrpvvqjjmnbcgezwfvqwnyisjcikslnywworgqkumbigekrfmihsmltvmuewlocqjyyoxdslamhjermikteawoxfquuzwozoxfvhfqozzfxupxmrzirstscqefztavjluurbaziiuixttyuadbvophauiwzxodqsllkhuqofdvwpfsphziddlvlkztipauevrcrksvpuwgxltithvkswoftkxygucnbwalovfjdlsidhqiixjluxgbdzvoaaulsyvhifcedghyzbtjqjfduyqdkinmpmyovqwdexnrpzbnazyfqvjqwmcrqufmawupnxefaixunlfwzzjpyoqqugykmqojgdiwemaujcqsdyvlaglksihcsywwbbgzhpejlmvbwtxlbakvgybodjhrtfxmrwybdojqgkvzacztstuywoklmxfmdewbkasogtabtsbaytwysxeupuiuwxoogsxqasbgtybqxjosohqzfvtosxkobcosyyhipnzwmlxbgqvhgjernpyrcvuayttskiqrrwwwfzlbsjyetqmspttlpitbacrtxojzeldtudpbigrbjbjgkwukllqdupesnmuendgdylcvrrwyjfizkpilhgisbhiolojsqbmqehswaptqvdznlesveppplsnzpuegefcwhhrqnolbgkrmejhynvsbasxmpopkxpepjfiqsgpbqnyfhrfzcfyitpkmzsfmkhleligfqzjusccpbtegjewrpgkjqdyfawjabqpjsfjoytjqhqkunrvzpzxjbagggnzftvrgjyixzkkddjroukpvovlajnzlytcdmzpyxpvxkfeqolndinwonmjwacfazpvrzgtpzqcfewzsrxzoxwxsfyvwjflhnxxiqsapudhvwthxfsjrabbltmurwnfwsxnfvvdzwekotfkwabeameutbfwsgvvmqaqexgxqeavivnysdrkeyiwuyoviparneaudpyqebjmqtadavcuhzwmaoewnwhznouqpymeyozldgepyjlhnrszxwsurzgqrtyilwzjvypcrqayswtqachqbkptzlnjurnawzqyaqpamaljmtejfpyrrvmducyrdjjbbtxtbqrxssqijpctzcvxcclsrwxwpdvnolysqgnllieezdwtvvzujanextmfbykqogkcwcubcxdqyyieifzemcdqswngmopoufbrnovtccedaldabxmqaabzqltcjrttxizevnrnmtmyivzqcuqgpdutiiyqhfxxwemmyiixlomiuhqzktklxwgnuomxsvwdflhkrrmwwvzfwmnerkqgakatfyrceuzywyxfdhisixzhavjlmehyklvhjztxepogfyvyqpogwqxrfqomcfgmysohaptzipfmjtnsqfktexqqzryglcbzsirwcjcfynmlbfziadzibaisdosapioaqgzmcnhjjoyungnvzmkvpzeumfbulbtvfdnzbfscumizqcapmeaouhoprcofqjpdsnlqmkphegtltsknunzigmixnagfgmhwrghwvwegaijwnkmraffvgttlmcklszyuyohkavhgnwkjvfpbxbasxmgrgcbyxilhondfmxrpnwuijujufponedqdnfficgwttnxemcbjlrgsbmslwjjqoslwneuqymldajdzdyiztddptxaaitmpmizwiodajusscojvgryvpfwuvsvsnotfnvwqzwroamjnaqmyacodoedsjbjdtdregvzxyovybajyxccjzafjeyiqsaxiflusiyhthbkrvzpamckjglwtlzusbfvtbxxcxdxpwwavcgmipwfecupzhphgsjabgkzwsbabdcqtphwqkxpsfwwuklsfdiiznpdcohcclmnamcovugpzbrehxfotjbdxxvpyfkcnpnwvbwkqeldcbkjrxyvvkqoxraqwaftgutgfshsdsywvsicbrwyjhfbzkhxvusewmkqjrtdlnvmtmkwxorfbreskduvclpqhhisjfuviilgdxwldnxwxdfmibczmyptjhawgpzquvpiemjlbpnpocwquecqkxxzwaxgmvwcmjmdacnauohqtajknuprxzuwbgzrvisbjjadnyxawzxyfqtmndhhfuwnlpsyeqwuzghpcmbicixfbmwrfrcugdplnekjzmjxnashqhskyeaoudaqjhgkwortfhzqarkqdbitmqqgwdukdgjzejtodegpredlwkeyygcftzawdrsowrzjlscbxxqkiryxyruvswmxngfkctjglfdxrqieoiajoiakmyqxoguwuffsbtngguekneeglbvhiqpiflyqkroxhdfikwhmotchizajztfihrzmmvnwqdjvspsmbetswubaafvhvhkkusmyskktcsnycodmjmvdtgpprnvhknbollwhrqueqlavuljmkphapixzkhoflhohwmdmhaubtwholnzpefgkgzzhkzdnntrufoyzwepfxmtfnpbewyoiacedmwedgyjdkxywxwbokcfpxywrkvbcqwwiqfyiygqqaoxtssszyjnbywofkwknlkmsmylofgjemslpwmcbrsjuxxehwfevkijufgbjszicufklgvcaxvzvzkxqxtowgrgofbwkwfygefwxcyxlgjgdcmxpvihgvwxoqwpmjkrewauygrnfgoeqrgpwvtbkpmbsxexqoecfeatubblgnmrtzzvsibviojfjftxiqxhxmjisxlgslhudjbrbrjrsdwrdxwmdltiymdmmgcklpmgfjntehaefmvukfpfsbbjsdtcpcppxkwoqgempyyobqaqdkohepjcasbfornxehvzfnochfmmjegwdesoyexqqzirutddokoofrhqwqfeovjikuhskzciwmbdfurdxkakccejvnizgmxaxffvxvzfanuzaiaayeqrcqfahqowhzrhxnivygwbozthyvofuutqnjukgmsxftjflcyqptiryxuzhrwnqfxqblunmikfylhpwofmfjeinpbkeojxypfdbrracaiuiwbwamkvozgguczzwxmbvozziwygpccemzqtgzzeqwblnitbrinqrbjholgwcivaevkzavnvkuxqemffjbwcsxeivpqqrfwrfshelxmyujyvguymqoavskmpegacbqhhpljsaapaeuiljkgbtlnlebubtinfynlhgvadmxhbzbjifsbtbitzinrgajbrhpecponumabqgecekdxydqufzfzbzoutrbypenuufgmajvndygalnnrggftlnuthaecczemghagdopomednlzgpryclyruelvpmvndgzaavopkixsgkrnlmnvstekraeretfdstjzcnwvfrhzmwmaizndibcuxnpxivsipyjnwxirxtodlzdknaughjltgpcszuxitcmamhzgklcclxrwichaiwpsrcntsdqfqwxdypvxpzjhfkktvtvwsqokoztkrdebtbbsbsrmaaigscicsdjktakejgdigqljuxaqhwazkgiqtwlcalpuhjpavpvwohkxnjknlwngkgetadoquzgjkllekmmbzkm";
        System.out.println(longestDupSubstring(s));
    }
}