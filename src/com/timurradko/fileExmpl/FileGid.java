package com.timurradko.fileExmpl;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class FileGid {
    private StringBuilder currentPath = new StringBuilder();
    private Scanner scanner = new Scanner(System.in);

    public File pave() {
        while (true) {
            try {
                choiceRoot();
                printCurFileContent();
                run();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                currentPath = new StringBuilder();
            }
        }
    }

    private File run() {
        while (true) {
            String input = scanner.nextLine();
            implInput(input);
            printCurFileContent();
            deleteFile(input);
        }
    }

    private void implInput(String input) {
        checkExit(input);
        checkBackToChoiceRoot(input);
        input = input.trim();
        if (input.startsWith("cd ")) {
            input = input.substring(3);
            input = input.trim();
            String[] pathParts = input.split("\\\\");
            for (String path : pathParts) {
                if (path.equals("..")) {
                    int slashIndex = currentPath.lastIndexOf("\\");
                    if (slashIndex != -1) {
                        currentPath.delete(slashIndex - 1, currentPath.length());
                        slashIndex = currentPath.lastIndexOf("\\");
                        currentPath.delete(slashIndex + 1, currentPath.length());
                    } else {
                        choiceRoot();
                    }
                } else {
                    currentPath.append(path).append("\\");
                }
            }
        }
    }

    private void deleteFile(String input) {
        checkExit(input);
        checkBackToChoiceRoot(input);
        input = input.trim();
        if (input.startsWith("del ")) {
            input = input.substring(4);
            input = input.trim();
            File file = new File(currentPath.toString());
            File[] files = file.listFiles();
            if (files != null) {
                for (File innerFile : files) {
                    String name = innerFile.getName();
                if (input.equals(name)) {
                    innerFile.delete();
                    System.out.println(name + " was deleted");
                }
            }
        }
    }
}

    private void choiceRoot() {
        File[] roots = File.listRoots();
        System.out.println(Arrays.toString(roots));
        String input = scanner.nextLine().toUpperCase();
        checkExit(input);
        for (File file : roots) {
            String path = file.getPath();
            if (path.startsWith(input)) {
                currentPath.append(path);
                return;
            }
        }
    }

    private void printCurFileContent() {
        File file = new File(currentPath.toString());
        if (!file.exists()) {
            throw new IllegalStateException("The file doesn't exist");
        }
        File[] files = file.listFiles();
        if (files != null) {
            if (files.length == 0) {
                System.out.println("Directory: " + file.getName() + " is empty");
            }
            for (File innerFile : files) {
                System.out.println(innerFile);
            }
        }
    }

    private void checkExit(String input) {
        String s = "exit";
        if (s.equalsIgnoreCase(input)) {
            System.exit(0);
        }
    }

    private void checkBackToChoiceRoot(String input) {
        String s = "back";
        if (s.equalsIgnoreCase(input)) {
            pave();
        }
    }

    public static void main(String[] args) {
        FileGid gid = new FileGid();
        gid.pave();
    }
}
