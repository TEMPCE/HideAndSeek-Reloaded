package com.tempce.hideandseek.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

public class Debug {
    public static void main(String[] args) {
        // Mavenのtargetディレクトリを指定（プロジェクトのルートからの相対パス）
        String targetDir = "target";
        // 移動先のディレクトリを指定（絶対パスまたは相対パス）
        String destinationDir = "D:\\Projects\\HideAndSeek-Reloaded\\run\\plugins";

        try {
            moveLatestJar(targetDir, destinationDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void moveLatestJar(String sourceDir, String destinationDir) throws IOException {
        File dir = new File(sourceDir);

        // target ディレクトリが存在しない場合は処理を終了
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("ターゲットディレクトリが存在しません: " + sourceDir);
            return;
        }

        // .jar ファイルをリストアップし、最も新しいファイルを取得
        Optional<File> latestJar = Arrays.stream(dir.listFiles((d, name) -> name.endsWith(".jar")))
                .max(Comparator.comparingLong(File::lastModified));

        if (latestJar.isPresent()) {
            File jarFile = latestJar.get();
            Path sourcePath = jarFile.toPath();
            Path destinationPath = Paths.get(destinationDir, jarFile.getName());

            // 移動先フォルダが存在しない場合は作成
            Files.createDirectories(Paths.get(destinationDir));

            // JARファイルを移動
            Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("最新のJARを移動しました: " + destinationPath);
        } else {
            System.out.println("JARファイルが見つかりませんでした。");
        }
    }
}
