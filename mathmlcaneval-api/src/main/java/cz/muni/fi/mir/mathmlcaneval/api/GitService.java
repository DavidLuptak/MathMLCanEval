/*
 * Copyright 2016 MIR@MU.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.muni.fi.mir.mathmlcaneval.api;

import cz.muni.fi.mir.mathmlcaneval.api.dto.GitBranchDTO;
import cz.muni.fi.mir.mathmlcaneval.api.dto.GitRevisionDTO;
import cz.muni.fi.mir.mathmlcaneval.api.dto.GitTagDTO;
import java.util.List;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface GitService
{
    void createRevision(GitRevisionDTO gitRevision) throws IllegalArgumentException;

    void deleteRevision(GitRevisionDTO gitRevision) throws IllegalArgumentException;

    void createBranch(GitBranchDTO gitBranch) throws IllegalArgumentException;

    void deleteBranch(GitBranchDTO gitBranch) throws IllegalArgumentException;

    void createTag(GitTagDTO gitTag) throws IllegalArgumentException;

    List<GitBranchDTO> getBranches();

    List<GitRevisionDTO> getRevisions(GitBranchDTO gitBranchDTO);

    List<GitTagDTO> getTags();
}