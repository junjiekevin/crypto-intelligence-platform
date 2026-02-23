from langchain_openai import ChatOpenAI
from app.schemas.tokenomics import TokenomicsOutput
from app.prompts.tokenomics_prompt import get_extraction_prompt
from typing import List

class TokenomicsExtractor:
    def __init__(self, model: str = "gpt-4o-mini"):
        # Wire up chain with structured output
        self.llm = ChatOpenAI(model=model, temperature=0) # Initialize LLM without structured output yet
        prompt = get_extraction_prompt()
        self.chain = prompt | self.llm.with_structured_output(TokenomicsOutput)
        self.prompt_template = get_extraction_prompt()

    def extract(self, context_chunks: List[str]) -> TokenomicsOutput:
        """
        Uses LLM to extract structured tokenomics from relevant context chunks.
        """
        context_text = "\n\n---\n\n".join(context_chunks)
        
        # Format the prompt
        messages = self.prompt_template.format_messages(context=context_text)
        
        # Call LLM and get structured output
        try:
            result = self.llm.invoke(messages)
            return result
        except Exception as e:
            raise RuntimeError(f"LLM extraction failed: {str(e)}")
        
