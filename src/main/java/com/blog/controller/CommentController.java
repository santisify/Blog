package com.blog.controller;

import com.blog.pojo.BlogComment;
import com.blog.service.CommentService;
import com.blog.util.YlbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    /**
     * 评论
     * @return
     */
    @PostMapping("/blog/comment/{bid}/{uid}")
    public String addComment2(@PathVariable String bid, @PathVariable String uid, BlogComment comment, Model model){

        try {
            //构造comment对象
            comment.setCommentId(YlbUtils.getUuid());
            comment.setCreateTime(YlbUtils.getTime());
            commentService.addComment(comment);

            List<BlogComment> commentList = commentService.getCommentsByBid(bid);
            model.addAttribute("commentList", commentList);
            return "page/read::reflash";
//        return MsgResult.success().add("commentList", commentList);
        } catch (Exception e) {
            logger.error("获取评论失败"+e);
            return "page/read::reflash";
        }
    }

    /**
     * 删除评论
     * @return
     */
    @GetMapping("/blog/comment/delete/{bid}/{cid}")
    public String delComment(@PathVariable String bid, @PathVariable String cid, Model model){

        try {
            //删除评论
            commentService.delCommentByCid(cid);

            List<BlogComment> commentList = commentService.getCommentsByBid(bid);
            model.addAttribute("commentList", commentList);
            return "page/read::reflash";
        } catch (Exception e) {
            logger.error("删除评论失败"+e);
            return "page/read::reflash";
        }
    }
}
