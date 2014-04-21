/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package cz.muni.fi.mir.services;

import cz.muni.fi.mir.db.domain.SourceDocument;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for translating files on given path into
 * SourceDocuments.
 *
 *
 * @author Dominik Szalai
 * @version 1.0
 * @since 1.0
 */
@Component("fileDirectoryService")
public class FileDirectoryService
{
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(FileDirectoryService.class);

    /**
     * Method used for obtaining SourceDocuments out of given path including
     * subdirectories matching fileName. Check out {@link MathFileVisitor} class
     * which is used for walking, to see the structure of fileName
     *
     * @param path root path in which are desired files
     * @param sdName name of the sourceDocument
     * @param fileName name of file to be matched
     * @return List of SourceDocuments on given path, empty List if no documents
     * are found.
     * @throws FileNotFoundException if root path does not exist
     */
    public SourceDocument exploreDirectory(String path, String sdName, String fileName) throws FileNotFoundException
    {
        Path p = Paths.get(path);

        return exploreDirectory(p, sdName, fileName);
    }

    /**
     * Method used for obtaining SourceDocuments out of given path including
     * subdirectories matching fileName. Check out {@link MathFileVisitor} class
     * which is used for walking, to see the structure of fileName
     *
     * @param path root path in which are desired files
     * @param sdName name of the Source document
     * @param fileName name of file to be matched
     * @return List of SourceDocuments on given path, empty List if no documents
     * are found.
     * @throws FileNotFoundException if root path does not exist
     */
    public SourceDocument exploreDirectory(Path path, String sdName,String fileName) throws FileNotFoundException
    {
        if (!Files.exists(path))
        {
            throw new FileNotFoundException("Root directory " + path + " not found.");
        }
        SourceDocument sd = new SourceDocument();
        sd.setDocumentName(sdName);

        MathFileVisitor mfv = new MathFileVisitor(fileName, MathFileVisitor.MathFileVisitorType.GLOB);
        try
        {
            Files.walkFileTree(path, mfv);
        }
        catch (IOException ex)
        {
            logger.error(ex);
        }
        
        List<String> paths = new ArrayList<>();

        for (Path p : mfv.done())
        {
            paths.add(p.toString());
        }

        sd.setDocumentPaths(paths);
        
        return sd;
    }
    
    @Deprecated
    public List<Path> getFoldersFromPath(Path path)
    {
        // vysledok
        List<Path> result = new ArrayList<>();
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(path))
        {
            // prebehneme vsetky podsubory
            for (Path p : ds)
            {
                result.add(p);
            }

        }
        catch (IOException io)
        {
            logger.error(io);
        }

        return result;
    }
    
    @Deprecated
    public List<Path> getFoldersFromPath(String path)
    {
        return getFoldersFromPath(Paths.get(path));
    }
}
