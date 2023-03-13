package alex.toy.nmj.member.presentation;

import alex.toy.nmj.member.application.MemberCommandService;
import alex.toy.nmj.member.application.MemberQueryService;
import alex.toy.nmj.member.presentation.dto.request.MemberCreateRequestDto;
import alex.toy.nmj.member.presentation.dto.request.MemberUpdateRequestDto;
import alex.toy.nmj.member.presentation.dto.response.MemberCreateResponse;
import alex.toy.nmj.member.presentation.dto.response.MemberResponse;
import alex.toy.nmj.member.presentation.dto.response.MemberUpdateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    public MemberController(
            final MemberCommandService memberCommandService,
            final MemberQueryService memberQueryService
    ) {
        this.memberCommandService = memberCommandService;
        this.memberQueryService = memberQueryService;
    }


    @GetMapping("/{memberId}")
    MemberResponse detail(@PathVariable final Long memberId) {
        return new MemberResponse(memberQueryService.findById(memberId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    MemberCreateResponse create(@RequestBody @Valid final MemberCreateRequestDto memberCreateRequestDto) {
        Long memberId = memberCommandService.save(memberCreateRequestDto);
        return new MemberCreateResponse(memberQueryService.findById(memberId));
    }

    @PatchMapping("/{memberId}")
    MemberUpdateResponse update(
            @PathVariable final Long memberId,
            @RequestBody @Valid final MemberUpdateRequestDto memberUpdateRequestDto
    ) {
        memberCommandService.update(memberId, memberUpdateRequestDto);
        return new MemberUpdateResponse(memberQueryService.findById(memberId));
    }

    @DeleteMapping("/{memberId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable final Long memberId) {
        memberCommandService.delete(memberId);
    }
}
