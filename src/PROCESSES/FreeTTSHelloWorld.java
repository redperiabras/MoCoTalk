package PROCESSES;

import com.sun.speech.freetts.VoiceManager;

public class FreeTTSHelloWorld {
	 
    /**
     * Example of how to list all the known voices.
     */
    public static void listAllVoices() {
        System.out.println();
        System.out.println("All voices available:");
        VoiceManager voiceManager = VoiceManager.getInstance();
        com.sun.speech.freetts.Voice[] voices = voiceManager.getVoices();
        for (int i = 0; i < voices.length; i++) {
            System.out.println("    " + voices[i].getName()
                               + " (" + voices[i].getDomain() + " domain)");
        }
    }
 
    public static void main(String[] args) {
          
        System.setProperty( "mbrola.base", "./System Files/mbrola" ); 
        listAllVoices();

        String voiceName = (args.length > 0)
            ? args[0]
        : "mbrola_us1";
 
        System.out.println("./mbrola");
        System.out.println("Using voice: " + voiceName);
 
        /* The VoiceManager manages all the voices for FreeTTS.
         */
        VoiceManager voiceManager = VoiceManager.getInstance();
        com.sun.speech.freetts.Voice helloVoice = voiceManager.getVoice(voiceName);
 
        if (helloVoice == null) {
            System.err.println(
                "Cannot find a voice named "
                + voiceName + ".  Please specify a different voice.");
            System.exit(1);
        }
 
        /* Allocates the resources for the voice.
         */
        helloVoice.allocate();
 
        /* Synthesize speech.
         */
        helloVoice.speak("Getting Emm-brolla to work with Windows was hard. "
                         + "I'm so glad to say hello to this world.");
 
        /* Clean up and leave.
         */
        helloVoice.deallocate();
        System.exit(0);
    }
}