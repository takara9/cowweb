package jp.gr.java_conf.cowweb;

import com.github.ricksbrown.cowsay.Cowsay;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Remote cowsay invoker using Spring Boot
 *
 * @author hhiroshell
 */
@RestController
@RequestMapping("/cowsay")
public class CowsayController {

    private static final String br = System.getProperty("line.separator");

    private static final List<String> cowfiles;

    static {
        List<String> infelicities = Arrays.asList(new String[]{"head-in", "telebears", "sodomized"});
        List<String> c = new ArrayList<>();
        Arrays.stream(Cowsay.say(new String[]{"-l"}).split(br)).forEach(f -> {
            if (!infelicities.contains(f)) {
                c.add(f);
            }
        });
        cowfiles = Collections.unmodifiableList(c);
    }

    /**
     * Return cowsay's 'say' message.
     *
     * @return Cowsay's 'say' message.
     */
    @RequestMapping("/say")
    public String say(@RequestParam String message) {
        Optional<String> msg = Optional.ofNullable(message);
        return Cowsay.say(new String[]{"-f", getRandomCowfile(), msg.orElse("Moo!")});
    }

    /**
     * Return cowsay's 'think' message.
     *
     * @return Cowsay's 'think' message.
     */
    @RequestMapping("/think")
    public String think(@RequestParam String message) {
        Optional<String> msg = Optional.ofNullable(message);
        return Cowsay.think(new String[]{"-f", getRandomCowfile(), msg.orElse("Moo!")});
    }

    private static String getRandomCowfile() {
        return cowfiles.get(new Random().nextInt(cowfiles.size()));
    }

    /**
     * Send back a fixed String.
     *
     * @return Send back a fixed String.
     */
    @RequestMapping("/")
    public String ping() {
        System.out.println("I'm working...");
        return "I'm working...";
    }

}