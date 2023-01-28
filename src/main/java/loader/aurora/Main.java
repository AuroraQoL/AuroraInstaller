package loader.aurora;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Random;

@Mod(modid = "loaderaurora", version = "1.0.0")
public class Main {

    private static File modFile = null;

    private void update() {
        try {
            InputStream in = new URL("https://github.com/Gabagooooooooooool/AuroraUpdater/releases/download/1.0/updater.jar").openStream();
            File updater = new File(System.getProperty("java.io.tmpdir") + "aurora_updater_" + new Random().nextInt() + ".jar");
            Files.copy(in, updater.toPath(), StandardCopyOption.REPLACE_EXISTING);
            ProcessBuilder pb = new ProcessBuilder("java", "-jar", "\"" + updater.getAbsolutePath() + "\"", "1000", "\"" + modFile.getAbsolutePath() + "\"", "mainrepo");
            Process p = pb.start();
            System.out.println("Updating...");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        modFile = event.getSourceFile();
        Runtime.getRuntime().addShutdownHook(new Thread(this::update));
        Minecraft.getMinecraft().shutdown();
    }
}
