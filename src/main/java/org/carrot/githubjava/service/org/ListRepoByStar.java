package org.carrot.githubjava.service.org;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.carrot.githubjava.entity.TopRepo;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class ListRepoByStar {
    @Autowired
    private GitHub myGithub;
    @Value("${carrot.github.orgs}")
    private List<String> orgs;

    @EventListener(ApplicationReadyEvent.class)
    private void printRepoByStar() throws IOException {
        ExcelWriter excelWriter = EasyExcel.write("./repos.xlsx", TopRepo.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .build();

        WriteSheet writeSheet;
        int sheetNo = 0;

        for (String org : orgs) {
            List<GHRepository> orgRepos = myGithub.getOrganization(org).listRepositories().toList();
            List<TopRepo> topRepos = new ArrayList<>();
            orgRepos.stream()
                    .sorted(Comparator.comparing(GHRepository::getStargazersCount).reversed())
                    .limit(30)
                    .forEach(repo -> topRepos.add(new TopRepo(repo.getName(), repo.getStargazersCount(), repo.getDescription(), repo.getHtmlUrl().toString())));

            writeSheet = EasyExcel.writerSheet(sheetNo++, org).build();
            excelWriter.write(topRepos, writeSheet);

            log.info("setting up " + org);
        }

        excelWriter.finish();
    }
}
