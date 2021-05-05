package main.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import main.api.response.TagResponse;
import main.dto.TagDto;
import main.model.Tags;
import main.model.repositories.TagRepository;
import org.springframework.stereotype.Service;

@Service
public class TagService {

  private final TagRepository tagRepository;
  private final TagResponse tagResponse = new TagResponse();
  private final HashMap<Tags, Integer> tagsWithName = new HashMap<>();


  List<TagDto> tagDtoList = new ArrayList<>();

  public TagService(TagRepository tagRepository) {
    this.tagRepository = tagRepository;
  }

  public TagResponse getTags() {

    List<Tags> tagsList = tagRepository.findAll(); // все тэги

    for (Tags tagName : tagsList) {
      tagsWithName
          .put(tagName, Collections.frequency(tagsList, tagName)); // к тэгу присваеваем его кол-во
    }

    int maxValue = Collections
        .max(tagsWithName.values()); //находим кол-во самого популярного тэга

    for (Tags tag : tagsList) {
      TagDto tagDto = new TagDto();

      double singleTagValue = tagsWithName.get(tag); // кол-во высчитываемого тэга
      double weights = tagsList.size(); // общее кол-во тэгов
      double dWeightSomeTag = singleTagValue / weights; // ненормированный вес
      double dWeightMax = maxValue / weights; // ненормированный вес максимального тэга
      double k = 1 / dWeightMax; // коэффициент k
      double tagWeight = dWeightSomeTag * k; // нормированный вес тэга

      tagDto.setName(tag.getName());
      tagDto.setWeight(tagWeight);

      tagDtoList.add(tagDto);
    }
    tagResponse.setTags(tagDtoList);

    return tagResponse;
  }
}
