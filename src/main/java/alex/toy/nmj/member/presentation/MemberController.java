package alex.toy.nmj.member.presentation;

import alex.toy.nmj.member.application.MemberService;
import alex.toy.nmj.member.presentation.dto.request.MemberCreateRequestDto;
import alex.toy.nmj.member.presentation.dto.response.MemberCreateResponse;
import org.springframework.http.HttpStatus;
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
        return new MemberCreateResponse(memberService.saveMember(memberCreateRequestDto));
    }
}
