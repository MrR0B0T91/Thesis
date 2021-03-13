package main.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import main.api.response.TagResponse;
import main.model.Tags;
import main.model.repositories.TagRepository;
import org.springframework.stereotype.Service;

@Service
public class TagService {

  private final TagRepository tagRepository;

  public TagService(TagRepository tagRepository) {
    this.tagRepository = tagRepository;
  }

  public TagResponse getTags(String name){

    HashMap<String, Double> tagHash = new HashMap<>();

    List<Tags> namedTag = tagRepository.findAllByName(name);
    Iterable<Tags> tagsIterable = tagRepository.findAll();
    List<Tags> allTags = new ArrayList<>();
    for (Tags tag : tagsIterable){
      allTags.add(tag);
    }

    double nameTagCount = namedTag.size();
    double allTagsCount = allTags.size();
    double weight = nameTagCount / allTagsCount;

    tagHash.put(name, weight);

    TagResponse tagResponse = new TagResponse();

    tagResponse.setTags(tagHash);

    return tagResponse;
  }
}
