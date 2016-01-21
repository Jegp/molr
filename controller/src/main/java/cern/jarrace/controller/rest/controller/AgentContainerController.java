package cern.jarrace.controller.rest.controller;

import cern.jarrace.controller.db.repository.AgentContainerRepository;
import cern.jarrace.controller.domain.AgentContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author timartin
 * Controller that exposes services to manage {@link AgentContainer}s
 */
@RestController
@RequestMapping("/jarrace/container")
public class AgentContainerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AgentContainerController.class);
    private static final File DEPLOYMENT_DIR = new File(System.getProperty("java.io.tmpdir"));

    @Autowired
    private AgentContainerRepository agentContainerRepository;

    @RequestMapping(value = "/deploy/{name}", method = RequestMethod.POST)
    public void deploy(@PathVariable("name") String name, @RequestBody byte[] jar) throws IOException {
        String path = writeFile(name, jar);
        startContainer(path);
    }

    private void startContainer(String path) throws IOException {
        String command = String.format("java -cp %s cern.jarrace.agent.AgentContainer", path);
        LOGGER.info(String.format("Starting agent container [%s]", command));
        new ProcessBuilder(command).start();
    }

    private String writeFile(String name, byte[] jar) throws IOException {
        File deploymentFile = DEPLOYMENT_DIR.toPath().resolve(name + ".jar").toFile();
        if (deploymentFile.exists()) {
            deploymentFile.delete();
        }
        deploymentFile.createNewFile();
        FileOutputStream outputStream = new FileOutputStream(deploymentFile);
        outputStream.write(jar);
        outputStream.close();
        LOGGER.info("Deployed file + " + deploymentFile.getAbsolutePath());
        return deploymentFile.getAbsolutePath();
    }

    @RequestMapping(value = "/register/{name}", method = RequestMethod.POST)
    public void register(@PathVariable(value = "name") String name, @RequestBody AgentContainer agentContainer) throws Exception {

        if (agentContainerRepository.findOne(name) != null) {
            throw new Exception("Agent container already registered");
        }

        agentContainer.setName(name);
        agentContainerRepository.save(agentContainer);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<AgentContainer> list() {
        return agentContainerRepository.findAll();
    }
}