package com.sample.boot2.repository;

import com.sample.boot2.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass() {
        System.out.println(memoRepository.getClass().getName());
    }

    @Test
    public void testInsertDummies() {

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("sample...." + i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void testSelect() {

        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("=============");

        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    @Transactional
    @Test
    public void testSelect2() {

        Long mno = 100L;

        Memo memo = memoRepository.getOne(mno);

        System.out.println("=============");

        System.out.println(memo);
    }

    @Test
    public void testUpdate() {

        Memo memo = Memo.builder().mno(100L).memoText("update Text").build();

        System.out.println(memoRepository.save(memo));
    }

    @Test
    public void testPageDefault() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println(result);

        System.out.println("------------------------------------");

        // 총 몇페이지인지
        System.out.println("total pages:  " + result.getTotalPages() );

        // 전체 개수가 몇개인지
        System.out.println("total count:  " + result.getTotalElements());

        // 현재 페이지 번호
        System.out.println("page number:  " + result.getNumber());

        // 페이지당 데이터 개수
        System.out.println("page size:  " + result.getSize());

        // 다음 페이지
        System.out.println("has next page?:  " + result.hasNext());

        // 시작 페이지 여부
        System.out.println("first page? :  " + result.isFirst());

        System.out.println("-------------------------------");
        for (Memo memo : result.getContent()) {
            System.out.println(memo);
        }
    }

    @Test
    public void testSort() {

        Sort sort1 = Sort.by("mno").descending();

        Pageable pageable = PageRequest.of(0, 10, sort1);

        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    @Test
    public void testSort1() {

        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2);

        Pageable pageable = PageRequest.of(0, 10, sortAll);

        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    @Test
    public void testQueryMethods() {

        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);

        for (Memo memo : list) {
            System.out.println(memo);
        }
    }

    @Test
    public void testQueryMethodWithPageable() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);

        result.get().forEach(memo -> {
            System.out.println(memo);
        });

        System.out.println(result.getTotalPages());
    }






}
