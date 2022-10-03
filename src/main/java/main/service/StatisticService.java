package main.service;

import java.util.Date;
import java.util.List;

import main.api.response.StatisticsResponse;
import main.model.PostVotes;
import main.model.Posts;
import main.model.repositories.PostRepository;
import main.model.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class StatisticService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public StatisticService(PostRepository postRepository,
                            UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public StatisticsResponse getMyStatistics() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        main.model.User currentUser = userRepository.findByEmail(user.getUsername());
        StatisticsResponse statisticsResponse = new StatisticsResponse();

        List<Posts> postsList = postRepository.findByUser(currentUser);
        int postCount = postsList.size();
        int countLikes = 0;
        int countDislikes = 0;
        int viewsCount = 0;
        Date date = postsList.get(0).getTime().getTime();
        long unixTime = date.getTime() / 1000;

        for (Posts post : postsList) {
            List<PostVotes> postVotesList = post.getPostVoteList();
            for (PostVotes postVote : postVotesList) {
                int value = postVote.getValue();
                if (value == 1) {
                    countLikes++;
                }
                if (value == -1) {
                    countDislikes++;
                }
            }
            viewsCount = viewsCount + post.getViewCount();
        }

        statisticsResponse.setPostsCount(postCount);
        statisticsResponse.setLikesCount(countLikes);
        statisticsResponse.setDislikesCount(countDislikes);
        statisticsResponse.setViewsCount(viewsCount);
        statisticsResponse.setFirstPublication(unixTime);

        return statisticsResponse;
    }

    public StatisticsResponse getAllStatistics() {
        StatisticsResponse allStatisticsResponse = new StatisticsResponse();

        List<Posts> postsList = postRepository.findAllForStatistics();
        int postCount = postsList.size();
        int countLikes = 0;
        int countDislikes = 0;
        int viewsCount = 0;
        Date date = postsList.get(0).getTime().getTime();
        long unixTime = date.getTime() / 1000;

        for (Posts post : postsList) {
            List<PostVotes> postVotesList = post.getPostVoteList();
            for (PostVotes postVote : postVotesList) {
                int value = postVote.getValue();
                if (value == 1) {
                    countLikes++;
                }
                if (value == -1) {
                    countDislikes++;
                }
            }
            viewsCount = viewsCount + post.getViewCount();
        }

        allStatisticsResponse.setPostsCount(postCount);
        allStatisticsResponse.setLikesCount(countLikes);
        allStatisticsResponse.setDislikesCount(countDislikes);
        allStatisticsResponse.setViewsCount(viewsCount);
        allStatisticsResponse.setFirstPublication(unixTime);

        return allStatisticsResponse;
    }
}
