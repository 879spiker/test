package com.Yushan.votesystem.controllers;

import com.Yushan.votesystem.models.VoteItem;
import com.Yushan.votesystem.repositories.VoteItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vote-items")
public class VoteItemController {
    @Autowired
    private VoteItemRepository voteItemRepository;

    // 返回所有投票項目
    @GetMapping("/")
    public List<VoteItem> getAllVoteItems() {
        return voteItemRepository.findAll();
    }

    // 新增投票項目
    @PostMapping("/")
    public VoteItem createVoteItem(@RequestBody VoteItem voteItem) {
        return voteItemRepository.save(voteItem);
    }

    // 更新投票項目 (可選)
    @PutMapping("/{id}")
    public ResponseEntity<VoteItem> updateVoteItem(@PathVariable Long id, @RequestBody VoteItem updatedVoteItem) {
        // 檢查投票項目是否存在
        VoteItem existingVoteItem = voteItemRepository.findById(id).orElse(null);
        if (existingVoteItem == null) {
            return ResponseEntity.notFound().build();
        }

        // 更新投票項目屬性
        existingVoteItem.setName(updatedVoteItem.getName());
        existingVoteItem.setDescription(updatedVoteItem.getDescription());

        // 保存更新後的投票項目
        VoteItem savedVoteItem = voteItemRepository.save(existingVoteItem);
        return ResponseEntity.ok(savedVoteItem);
    }

    // 刪除投票項目 (可選)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoteItem(@PathVariable Long id) {
        // 檢查投票項目是否存在
        VoteItem existingVoteItem = voteItemRepository.findById(id).orElse(null);
        if (existingVoteItem == null) {
            return ResponseEntity.notFound().build();
        }

        // 刪除投票項目
        voteItemRepository.delete(existingVoteItem);
        return ResponseEntity.noContent().build();
    }

    // 使用者投票功能 (假設投票時只增加票數)
    @PostMapping("/vote/{id}")
    public ResponseEntity<VoteItem> voteForItem(@PathVariable Long id) {
        // 檢查投票項目是否存在
        VoteItem voteItem = voteItemRepository.findById(id).orElse(null);
        if (voteItem == null) {
            return ResponseEntity.notFound().build();
        }

        // 增加票數
        voteItem.setVoteCount(voteItem.getVoteCount() + 1);
        VoteItem updatedVoteItem = voteItemRepository.save(voteItem);

        return ResponseEntity.ok(updatedVoteItem);
    }
}

