package com.ytci.kristminer;

import org.apache.commons.cli.*;

/**
 * Acts as a container and parser for the command line arguments passed into the tool.
 *
 * @author Lignum
 */
public class KristMinerConfig {
    private String[] args;
    private Options options = new Options();

    /** Did parsing the command line succeed? */
    private boolean success;

    private String address, prefix;

    /**
     * @param args The command line arguments to be parsed.
     */
    public KristMinerConfig(String[] args) {
        this.args = args;

        // TODO: Localise the descriptions?
        options.addOption("a", "address", true, "The Krist address to mine for.");
        options.addOption("p", "prefix", true, "A prefix for the submitted nonces.");
        options.addOption("?", "help", false, "Show this help.");

        success = parse();
    }

    /**
     * Manually set address and prefix
     * @param address Address to mine for
     * @param prefix Prefix for nonces
     */
    public KristMinerConfig(String address, String prefix) {
    	this.address = address;
    	this.prefix = prefix;
    	success = true;
    }
    
    /**
     * Parses the command line args.
     * @return Whether parsing succeeded or not.
     */
    private boolean parse() {
        final CommandLineParser parser = new DefaultParser();

        try {
            final CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption('?')) {
                new HelpFormatter().printHelp("java -jar KristMiner.jar", options);
                return false; // User requested help; do nothing
            }

            if (!cmd.hasOption('a')) {
                System.err.println("You must specify an address with --address/-a!");
                return false;
            }

            if (cmd.hasOption('p')) {
                prefix = cmd.getOptionValue('p');
            } else {
                prefix = ""; // Fulfill the promise getPrefix() makes.
            }

            address = cmd.getOptionValue('a');
            return true; // If we got this far, we were successful.
        } catch (ParseException e) {
            System.err.println("Couldn't parse your command line.");
            return false;
        }
    }

    /**
     * @return Whether the command line wasn't malformed.
     */
    public boolean didSucceed() {
        return success;
    }

    /**
     * @return The Krist address specified in the command line.
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return The nonce prefix specified in the command line or an empty string if it wasn't specified.
     */
    public String getPrefix() {
        return prefix;
    }
}