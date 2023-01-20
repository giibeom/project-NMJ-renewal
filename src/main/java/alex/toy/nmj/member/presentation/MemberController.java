package alex.toy.nmj.member.presentation;

import alex.toy.nmj.member.application.MemberService;
import alex.toy.nmj.member.presentation.dto.request.MemberCreateRequestDto;
import alex.toy.nmj.member.presentation.dto.request.MemberUpdateRequestDto;
import alex.toy.nmj.member.presentation.dto.response.MemberCreateResponse;
import alex.toy.nmj.member.presentation.dto.response.MemberUpdateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberCreateResponse create(@RequestBody @Valid final MemberCreateRequestDto memberCreateRequestDto) {
        return new MemberCreateResponse(memberService.save(memberCreateRequestDto));
    }

    @PatchMapping("/{memberId}")
    public MemberUpdateResponse update(@PathVariable final Long memberId,
                                       @RequestBody @Valid final MemberUpdateRequestDto memberUpdateRequestDto) {
        return new MemberUpdateResponse(memberService.update(memberId, memberUpdateRequestDto));
    }

    @DeleteMapping("/{memberId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long memberId) {
        memberService.delete(memberId);
    }
}
