package official.o2023.usopen.bronze;

import java.io.*;
import java.util.*;

/** 2023 us open bronze */
public class MooLanguage {
    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int testNum = Integer.parseInt(read.readLine());
        for (int t = 0; t < testNum; t++) {
            StringTokenizer initial = new StringTokenizer(read.readLine());
            int wordNum = Integer.parseInt(initial.nextToken());
            int commaNum = Integer.parseInt(initial.nextToken());
            int periodNum = Integer.parseInt(initial.nextToken());
            Map<String, List<String>> words = new HashMap<>() {{
                put("noun", new ArrayList<>());
                put("transitive-verb", new ArrayList<>());
                put("intransitive-verb", new ArrayList<>());
                put("conjunction", new ArrayList<>());
            }};
            for (int w = 0; w < wordNum; w++) {
                StringTokenizer word = new StringTokenizer(read.readLine());
                String token = word.nextToken();
                String type = word.nextToken();
                words.get(type).add(token);
            }

            int maxSize = 0;
            int bestTV = 0;
            int bestITV = 0;
            final List<String> nouns = words.get("noun");  // just a shorthand
            final int maxVerbs = periodNum + Math.min(periodNum, words.get("conjunction").size());
            for (int tv = 0; tv <= words.get("transitive-verb").size(); tv++) {
                for (int itv = 0; itv <= words.get("intransitive-verb").size(); itv++) {
                    final int nounsLeft = nouns.size() - itv - 2 * tv;
                    if (nounsLeft < 0 || tv + itv > maxVerbs) {
                        break;
                    }
                    final int conjUsed = Math.min(words.get("conjunction").size(), (tv + itv) / 2);
                    final int extra = tv > 0 ? Math.min(commaNum, nounsLeft) : 0;
                    final int total = 2 * itv + 3 * tv + extra + conjUsed;
                    if (total > maxSize) {
                        maxSize = total;
                        bestTV = tv;
                        bestITV = itv;
                    }
                }
            }

            int nounAt = 0;
            List<String> phrases = new ArrayList<>();
            for (int i = 0; i < bestITV; i++) {
                String itv = words.get("intransitive-verb").get(i);
                String noun = nouns.get(nounAt++);
                phrases.add(noun + " " + itv);
            }
            for (int i = 0; i < bestTV; i++) {
                String tv = words.get("transitive-verb").get(i);
                String noun1 = nouns.get(nounAt++);
                String noun2 = nouns.get(nounAt++);
                phrases.add(noun1 + " " + tv + " " + noun2);
            }
            if (bestTV > 0) {
                StringBuilder last = new StringBuilder(phrases.get(phrases.size() - 1));
                for (int c = 0; c < commaNum && nounAt < nouns.size(); c++) {
                    last.append(", ").append(nouns.get(nounAt++));
                }
                phrases.set(phrases.size() - 1, last.toString());
            }

            int phraseAt = 0;
            List<String> sentences = new ArrayList<>();
            for (String conj : words.get("conjunction")) {
                if (phraseAt + 1 >= phrases.size()) {
                    break;
                }
                String first = phrases.get(phraseAt++);
                String second = phrases.get(phraseAt++);
                sentences.add(first + " " + conj + " " + second);
            }
            sentences.addAll(phrases.subList(phraseAt, phrases.size()));

            System.out.println(maxSize);
            String res = String.join(". ", sentences);
            if (!res.isEmpty()) {
                res = res + ".";
            }
            System.out.println(res);
        }
    }
}
